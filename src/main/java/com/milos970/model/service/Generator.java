package com.milos970.model.service;

import com.milos970.model.entity.*;
import com.milos970.structure.AVLTree;

import java.util.*;

public final class Generator
{
    private final Random random;

    private static final int MAX_NUMBER_OF_REGIONS = 1_000;
    private static final int MAX_NUMBER_OF_DISTRICTS = 10_000;
    private static final int MAX_NUMBER_OF_WORKPLACES = 50_000;
    private static final int MAX_NUMBER_OF_PATIENTS = 100_000;
    private static final int MAX_NUMBER_OF_PCR_TESTS = 1_000_000;

    private int numberOfRegions;
    private int numberOfDistricts;
    private int numberOfWorkplaces;
    private int numberOfPatients;
    private int numberOfTests;

    public Generator(int numOfRegions, int numberOfDistricts, int numberOfWorkplaces, int numberOfPatients, int numberOfTests) {
        if (numOfRegions > MAX_NUMBER_OF_REGIONS
                || numberOfDistricts > MAX_NUMBER_OF_DISTRICTS
                || numberOfWorkplaces > MAX_NUMBER_OF_WORKPLACES
                || numberOfPatients > MAX_NUMBER_OF_PATIENTS
                || numberOfTests > MAX_NUMBER_OF_PCR_TESTS) {
            throw new IllegalArgumentException();

        }

        if (numOfRegions > numberOfDistricts) {
            throw new IllegalArgumentException("Počet krajov nemôže byť väčší ako počet okresov.");
        }

        if (numberOfDistricts > numberOfWorkplaces) {
            throw new IllegalArgumentException("Počet okresov nemôže byť väčší ako počet pracovísk.");
        }

        this.numberOfRegions = numOfRegions;
        this.numberOfDistricts = numberOfDistricts;
        this.numberOfWorkplaces = numberOfWorkplaces;
        this.numberOfPatients = numberOfPatients;
        this.numberOfTests = numberOfTests;

        this.random = new Random();
    }


    public Iterable<Region> generateRegions(int number) {

        List<Region> regionList = new ArrayList<>();

        for (int i = 1; i <= numberOfRegions; ++i) {
            regionList.add(new Region(i, new AVLTree<Integer, District>()));
        }

        return regionList;
    }

    public Iterable<District> generateDistricts() {
        List<District> districtList = new ArrayList<>();

        for (int i = 1; i <= numberOfDistricts; ++i) {
            districtList.add(new District(i*10, new AVLTree<Integer, Workplace>()));
        }

        return districtList;
    }

    public Iterable<Workplace> generateWorkplaces() {
        List<Workplace> workplaceList = new ArrayList<>();

        for (int i = 1; i <= numberOfDistricts; ++i) {
            workplaceList.add(new Workplace(i * 100));
        }

        return workplaceList;
    }

    public Iterable<PCRTest> generatePCRTests() {
        List<PCRTest> testList = new ArrayList<>();

        for (int i = 1; i <= numberOfTests; ++i) {
            testList.add(new PCRTest(i * 100));
        }

        return testList;
    }

    public Iterable<Patient> generatePatients() {

    }


    //pouzijem AVL, kedze sa jedna o len raz vkladanie a degeneroval by
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

            var districtTree = new AVLTree<>();

            for (int districtCode : districtCodes) {
                var workplacesTree = new AVLTree<Integer, Workplace>();

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
}
