package com.milos970.model.entity;

import com.milos970.structure.BSTree;

import java.time.LocalDateTime;

public class Workplace
{
    private final int id;
    private final BSTree<LocalDateTime, PCRTest> positiveTests;
    private final BSTree<LocalDateTime, PCRTest> negativeTests;

    public Workplace(int id) {
        this.id = id;
        this.positiveTests = new BSTree<>();
        this.negativeTests = new BSTree<>();
    }


    public int id() {
        return id;
    }

    public void addPositivePCRTest(PCRTest test) {
        this.positiveTests.insert(test.getDateTime(), test);
    }
    public void addNegativePCRTest(PCRTest test) {
        this.negativeTests.insert(test.getDateTime(), test);
    }

    public BSTree<LocalDateTime, PCRTest> getPositiveTests() {
        return positiveTests;
    }

    public BSTree<LocalDateTime, PCRTest> getNegativeTests() {
        return negativeTests;
    }
}
