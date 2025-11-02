package com.milos970.repository;

import com.milos970.model.PCRTest;
import com.milos970.structure.BSTree;

import java.time.LocalDateTime;
import java.util.*;

public final class PCRRepository  {
    private final BSTree<Integer, Region> regions;
    private final BSTree<Integer, District> districts;
    private final BSTree<Integer, Workplace> workplaces;

    private final BSTree<Integer, PCRTest> testsById;

    private final BSTree<LocalDateTime, PCRTest> positiveTestsByDate;
    private final BSTree<LocalDateTime, PCRTest> negativeTestsBYDate;


    public PCRRepository() {
        this.testsById = new BSTree<>();
        this.regions = new BSTree<>();
        this.districts = new BSTree<>();
        this.workplaces = new BSTree<>();

        this.positiveTestsByDate = new BSTree<>();
        this.negativeTestsBYDate = new BSTree<>();

        initRegionsAndDistricts();
    }

    private void initRegionsAndDistricts() {
        Map<Integer, int[]> data = new LinkedHashMap<>();
        data.put(101, new int[]{10101, 10102, 10103, 10104, 10105, 10106, 10107, 10108}); // Bratislavský
        data.put(201, new int[]{20101, 20102, 20103, 20104, 20105, 20106, 20107});         // Trnavský
        data.put(301, new int[]{30101, 30102, 30103, 30104, 30105, 30106, 30107});         // Trenčiansky
        data.put(401, new int[]{40101, 40102, 40103, 40104, 40105, 40106, 40107, 40108}); // Nitriansky
        data.put(501, new int[]{50101, 50102, 50103, 50104, 50105, 50106, 50107});         // Žilinský
        data.put(601, new int[]{60101, 60102, 60103, 60104, 60105, 60106, 60107, 60108}); // Banskobystrický
        data.put(701, new int[]{70101, 70102, 70103, 70104, 70105, 70106, 70107});         // Prešovský
        data.put(801, new int[]{80101, 80102, 80103, 80104, 80105, 80106});                // Košický


        for (Map.Entry<Integer, int[]> entry : data.entrySet()) {
            int regionCode = entry.getKey();
            int[] districtCodes = entry.getValue();

            var districtTree = new BSTree<Integer, District>();

            for (int districtCode : districtCodes) {
                var workplacesTree = new BSTree<Integer, Workplace>();

                for (int i = 1; i <= 15; ++i) {
                    int workplaceId = i * 50 + districtCode;
                    var workplace = new Workplace(workplaceId);
                    workplacesTree.insert(workplaceId, workplace);
                    this.workplaces.insert(workplaceId, workplace);
                }

                var district = new District(districtCode, workplacesTree);
                districtTree.insert(districtCode, district);
                this.districts.insert(districtCode, district);
            }

            var region = new Region(regionCode, districtTree);
            this.regions.insert(regionCode, region);
        }

    }



    public void save(PCRTest entity) {
        this.testsById.insert(entity.id(), entity);

        int region = entity.region();
        int district = entity.district();
        int workplace = entity.workplace();

        this.regions.find(region).get().getDistricts().find(district).get().getWorkplaces().find(workplace).get().addPCRTest(entity);


    }


    public void removeById(int id) {
        PCRTest test = this.testsById.delete(id);
        this.negativeTestsBYDate.delete(test.date());
        this.positiveTestsByDate.delete(test.date());
    }

    public Optional<PCRTest> findById(int id)
    {
        return this.testsById.find(id);
    }


    public Optional<District> findDistrictById(int id) {
        return this.districts.find(id);
    }

    public Optional<Region> findRegionById(int id) {
        return this.regions.find(id);
    }

    public Optional<Workplace> findWorkplaceById(int id) {
        return this.workplaces.find(id);
    }

    //4,5
    public Iterable<PCRTest> findAllPositiveByDistrict(District district, LocalDateTime from, LocalDateTime to) {
        return this.findTestsDistrict(district, from, to,true);
    }

    public Iterable<PCRTest> findAllByDistrict(District district, LocalDateTime from, LocalDateTime to) {
        return this.findTestsDistrict(district, from, to,false);
    }

    private Iterable<PCRTest> findTestsDistrict(District district, LocalDateTime from, LocalDateTime to, boolean onlyPositive) {
        var allWorkplaces = district.getWorkplaces().inOrderValues();

        List<PCRTest> result = new ArrayList<>();

        for (Workplace workplace : allWorkplaces) {

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
    public Iterable<PCRTest> findAllByRegion(Region region, LocalDateTime from, LocalDateTime to) {
        return this.findTestsByRegion(region, from, to, false);
    }

    public Iterable<PCRTest> findAllPositiveByRegion(Region region, LocalDateTime from, LocalDateTime to) {
        return this.findTestsByRegion(region, from, to, true);
    }

    private Iterable<PCRTest> findTestsByRegion(Region region, LocalDateTime from, LocalDateTime to, boolean onlyPositive) {
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


    //8,9
    public Iterable<PCRTest> findAllPositiveBetweenDates(LocalDateTime from, LocalDateTime to) {
        return this.findTestsBetweenDates(from, to, true);
    }

    public Iterable<PCRTest> findAllBetweenDates(LocalDateTime from, LocalDateTime to) {
        return this.findTestsBetweenDates(from, to, false);
    }


    private Iterable<PCRTest> findTestsBetweenDates(LocalDateTime from, LocalDateTime to, boolean onlyPositive) {
        List<PCRTest> allTests = new ArrayList<>();

        if (!onlyPositive) {
            allTests.addAll(this.negativeTestsBYDate.intervalSearch(from,to));
        }

        allTests.addAll(this.positiveTestsByDate.intervalSearch(from,to));

        return allTests;
    }
    //8,9




    public Iterable<PCRTest> findAll(LocalDateTime from, LocalDateTime to) {
        return this.positiveTestsByDate.intervalSearch(from, to);
    }

































    public List<PCRTest> findAllByRegionId(int regionId, LocalDateTime from, LocalDateTime to) {
        var regionOpt = this.regions.find(regionId);
        if (regionOpt.isEmpty()) return List.of();

        var region = regionOpt.get();
        var allDistricts = region.getDistricts().inOrderValues();

        List<PCRTest> result = new ArrayList<>();

        for (var district : allDistricts) {
            var allWorkplaces = district.getWorkplaces().inOrderValues();

            for (var workplace : allWorkplaces) {
                var testsInRange = workplace.getPcrTests().intervalSearch(from, to);
                result.addAll(testsInRange);
            }
        }

        return result;
    }



    //14
    public List<PCRTest> findAllByDistrict(LocalDateTime from, LocalDateTime to) {
        List<PCRTest> result = new ArrayList<>();

        var allDistricts = this.districts.inOrderValues();


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
    public List<District> findDistrictsBetweenDates(LocalDateTime from, LocalDateTime to) {

        var allDistricts = this.districts.inOrderValues();
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





}
