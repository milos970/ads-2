package com.milos970.repository;

import com.milos970.model.PCRTest;
import com.milos970.model.Patient;
import com.milos970.structure.BSTree;

import java.time.LocalDateTime;
import java.util.*;

public class PCRRepository implements Repository<PCRTest> {

    private final BSTree<Integer, Region> regions;
    private final BSTree<Integer, District> districts;
    private final BSTree<Integer, Workplace> workplaces;

    private final BSTree<LocalDateTime, PCRTest> testsByDate;
    private final BSTree<Integer, PCRTest> testsById;
    private final BSTree<Double, PCRTest> testsById;

    public PCRRepository() {
        this.testsById = new BSTree<>();
        this.regions = new BSTree<>();
        this.districts = new BSTree<>();
        this.workplaces = new BSTree<>();
        this.testsByDate = new BSTree<>();

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




    @Override
    public void save(PCRTest entity) {
        this.testsById.insert(entity.id(), entity);

        int region = entity.region();
        int district = entity.district();
        int workplace = entity.workplace();

        this.regions.find(region).get().getDistricts().find(district).get().getWorkplaces().find(workplace).get().addPCRTest(entity);


    }

    @Override
    public void removeById(int id) {

    }

    public List<PCRTest> findByPatientId(int id) {
        return null;
    }

    public List<PCRTest> findByIdWorkPlace(int id) {
        //3
        return null;
    }

    @Override
    public PCRTest findById(int id) {

        //hashMap(testy, strom)
        //operacie 1,
        return null;
    }

    @Override
    public List<PCRTest> findByDistrictId(int id) {
        return List.of();
    }


    //v service mi da aj pozitivne(aplikovat aj na 10 aj na 11)
    public List<PCRTest> findTestByDistrictId(int districtId, LocalDateTime from, LocalDateTime to) {
        var districtOpt = this.districts.find(districtId);
        if (districtOpt.isEmpty()) return List.of();

        var district = districtOpt.get();
        var allWorkplaces = district.getWorkplaces().inOrderValues();

        List<PCRTest> result = new ArrayList<>();

        for (var workplace : allWorkplaces) {
            var testsInRange = workplace.getPcrTests().intervalSearch(from, to);
            result.addAll(testsInRange);
        }

        return result;
    }

    public List<PCRTest> findTestsByIdPatient(int id) {
        return this.testsByDate.inOrderValues().stream().filter(test -> test.id() == id).toList();
    }


    //filtracia v service podla pozitivity (uloha 6 a 7 12 )
    @Override
    public List<PCRTest> findByRegionId(int regionId, LocalDateTime from, LocalDateTime to) {
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

    //filtracia v service podla pozitivity (uloha 8 a 9)
    @Override
    public List<PCRTest> findByDateBetween(LocalDateTime from, LocalDateTime to) {
        List<PCRTest> result = new ArrayList<>();

        var allRegions = this.regions.inOrderValues();

        for (var region : allRegions) {
            var allDistricts = region.getDistricts().inOrderValues();

            for (var district : allDistricts) {
                var allWorkplaces = district.getWorkplaces().inOrderValues();

                for (var workplace : allWorkplaces) {
                    var testsInRange = workplace.getPcrTests().intervalSearch(from, to);
                    result.addAll(testsInRange);
                }
            }
        }

        return result;
    }

    //14
    public List<Patient> findTestByDistrict(LocalDateTime from, LocalDateTime to) {
        List<PCRTest> result = new ArrayList<>();

        var allDistricts = this.districts.inOrderValues();


        for (var district : allDistricts) {
            var workplaces = district.getWorkplaces().inOrderValues();
            PCRTest testCandidate = null;
            for (var workplace : workplaces) {
                var testsInRange = workplace.getPcrTests().intervalSearch(from, to);

                var bestTest = testsInRange.stream()
                        .max(Comparator.comparingDouble(PCRTest::value))
                        .orElse(null);

                if (bestTest != null && (testCandidate == null || bestTest.value() > testCandidate.value())) {
                    testCandidate = bestTest;
                }
            }
            result.add(testCandidate); //tu bude Patient

        }

        return result;
    }

    //15
    public List<District> findDistricts(LocalDateTime from, LocalDateTime to) {

        var allDistricts = this.districts.inOrderValues();
        Map<District, Integer> map = new HashMap<>();


        for (var district : allDistricts) {
            var workplaces = district.getWorkplaces().inOrderValues();

            int count = 0;
            for (var workplace : workplaces) {
                var testsInRange = workplace.getPcrTests().intervalSearch(from, to);
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
