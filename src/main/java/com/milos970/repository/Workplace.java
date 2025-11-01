package com.milos970.repository;

import com.milos970.model.PCRTest;
import com.milos970.structure.BSTree;

import java.time.LocalDateTime;

public class Workplace
{
    private final int id;
    private final BSTree<LocalDateTime, PCRTest> pcrTests;

    public Workplace(int id) {
        this.id = id;
        this.pcrTests = new BSTree<>();
    }


    public void addPCRTest(PCRTest test) {
        this.pcrTests.insert(test.date(), test);
    }

    public BSTree<LocalDateTime, PCRTest> getPcrTests() {
        return pcrTests;
    }
}
