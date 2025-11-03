package com.milos970.model.repository;

import com.milos970.model.entity.District;
import com.milos970.model.entity.PCRTest;
import com.milos970.model.entity.Region;
import com.milos970.model.entity.Workplace;
import com.milos970.structure.InMemoryDatabase;

import java.time.LocalDateTime;
import java.util.*;

public final class PCRTestRepository {
    private final InMemoryDatabase database;

    public PCRTestRepository(InMemoryDatabase database) {
        this.database = database;
    }

    //1
    public void save(PCRTest entity)
    {
        int regionId = entity.regionId();
        int districtId = entity.districtId();
        int workplaceId = entity.workplaceId();


        Region region = this.database.findRegionByPk(regionId).orElseThrow(NoSuchElementException::new);
        District district = region.getDistricts().find(districtId).orElseThrow(NoSuchElementException::new);
        Workplace workplace = district.getWorkplaces().find(workplaceId).orElseThrow(NoSuchElementException::new);

        if (entity.result())
        {
            workplace.addPositivePCRTest(entity);
        } else {
            workplace.addNegativePCRTest(entity);
        }

        this.database.insertIntoTablePCRTests(entity);
        this.database.insertIntoTablePCRTestsByPatientId(entity);
    }

    //2
    public Optional<PCRTest> findByPatientId(String patientId, int testId) {
        return this.database.findTestByPatientPk(patientId, testId);
    }

    //3
    public List<PCRTest> findAllByPatientId(String patientId) {
        return this.database.findAllTestsByPatientPk(patientId);
    }





    //18
    public Optional<PCRTest> findById(int id) {
        return this.database.findPCRTestByPk(id);
    }


    public void removeById(int id) {

    }





    //4,5,10
    public Iterable<PCRTest> findAllPositiveByDistrictId(int id, LocalDateTime from, LocalDateTime to) {
        return this.findTestsDistrictId(id, from, to,true);
    }



    public Iterable<PCRTest> findAllByDistrictId(int id, LocalDateTime from, LocalDateTime to) {
        return this.findTestsDistrictId(id, from, to,false);
    }

    private Iterable<PCRTest> findTestsDistrictId(int id, LocalDateTime from, LocalDateTime to, boolean onlyPositive) {
        District district = this.database.findDistrictByPk(id).orElseThrow(NoClassDefFoundError::new);
        List<Workplace> workplaces = district.getWorkplaces().inOrderValues();



        List<PCRTest> result = new ArrayList<>();

        for (Workplace workplace : workplaces) {

            if (onlyPositive) {
                result.addAll(workplace.getPositiveTests().intervalSearch(from, to));
            } else {
                result.addAll(workplace.getPositiveTests().intervalSearch(from, to));
                result.addAll(workplace.getNegativeTests().intervalSearch(from, to));
            }
        }

        return result;
    }
    //4

    //6,7
    public Iterable<PCRTest> findAllByRegionId(int id, LocalDateTime from, LocalDateTime to) {
        return this.findTestsByRegion(id, from, to, false);
    }


    //12
    public Iterable<PCRTest> findAllPositiveByRegionId(int id, LocalDateTime from, LocalDateTime to) {
        return this.findTestsByRegion(id, from, to, true);
    }

    private Iterable<PCRTest> findTestsByRegion(int id, LocalDateTime from, LocalDateTime to, boolean onlyPositive) {
        Region region = this.database.findRegionByPk(id).orElseThrow(NoClassDefFoundError::new);

        var districts = region.getDistricts().inOrderValues();
        List<PCRTest> allTests = new ArrayList<>();

        for (District district : districts) {
            var workplaces = district.getWorkplaces().inOrderValues();

            for (Workplace workplace : workplaces) {
                if (onlyPositive) {
                    allTests.addAll(workplace.getPositiveTests().intervalSearch(from, to));
                } else {
                    allTests.addAll(workplace.getPositiveTests().intervalSearch(from, to));
                    allTests.addAll(workplace.getNegativeTests().intervalSearch(from, to));
                }
            }
        }

        return allTests;
    }
    //6,7


    //8,9,13
    public Iterable<PCRTest> findAllPositiveBetweenDates(LocalDateTime from, LocalDateTime to) {
        return this.findTestsBetweenDates(from, to, true);
    }

    public Iterable<PCRTest> findAllBetweenDates(LocalDateTime from, LocalDateTime to) {
        return this.findTestsBetweenDates(from, to, false);
    }


    private Iterable<PCRTest> findTestsBetweenDates(LocalDateTime from, LocalDateTime to, boolean onlyPositive) {
        List<PCRTest> allTests = new ArrayList<>();

        if (onlyPositive) {
            allTests.addAll(this.database.findAllPositiveTestsByDate(from, to));
        } else {
            allTests.addAll(this.database.findAllTestsByDate(from, to));
        }



        return allTests;
    }
    //8,9





//10 spravit v service
//11 spravit v service
// 12 spravit v service
// 13 spravit v service
































    //14
    public List<PCRTest> findAllByDistrict(LocalDateTime from, LocalDateTime to) {
        List<PCRTest> result = new ArrayList<>();

        var allDistricts = this.database.findAllDistricts();


        for (var district : allDistricts) {
            var workplaces = district.getWorkplaces().inOrderValues();
            PCRTest testCandidate = null;
            for (var workplace : workplaces) {
                var testsInRange = workplace.getPositiveTests().intervalSearch(from, to);

                var bestTest = testsInRange.stream()
                        .max(Comparator.comparingDouble(PCRTest::value))
                        .orElse(null);

                if (bestTest != null && (testCandidate == null || bestTest.value() > testCandidate.value())) {
                    testCandidate = bestTest;
                }
            }
            result.add(testCandidate);

        }

        return result;
    }

    //15
    public List<District> findAllDistricts(LocalDateTime from, LocalDateTime to) {

        var allDistricts = this.database.findAllDistricts();
        Map<District, Integer> map = new HashMap<>();

        for (var district : allDistricts) {
            var workplaces = district.getWorkplaces().inOrderValues();

            int count = 0;
            for (var workplace : workplaces) {
                var testsInRange = workplace.getPositiveTests().intervalSearch(from, to);
                count += testsInRange.size();
            }
            map.put(district, count);
        }

        List<District> result = map.entrySet().stream()
                .sorted(Map.Entry.<District, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .toList();


        return result;
    }

    //16
    public List<Region> findAllRegions(LocalDateTime from, LocalDateTime to) {

        var regions = this.database.findAllRegions();
        Map<Region, Integer> map = new HashMap<>();

        for (var region : regions) {
            var districts = region.getDistricts().inOrderValues();

            int count = 0;
            for (var district : districts) {
                var workplaces = district.getWorkplaces().inOrderValues();

                for (var workplace : workplaces) {
                    var testsInRange = workplace.getPositiveTests().intervalSearch(from, to);
                    count += testsInRange.size();
                }

            }
            map.put(region, count);
        }

        List<Region> result = map.entrySet().stream()
                .sorted(Map.Entry.<Region,Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .toList();


        return result;
    }








}
