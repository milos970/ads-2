package com.milos970.model.entity;

import com.milos970.structure.BSTree;

import java.time.LocalDateTime;

public class Workplace
{
    private final int id;
    private final BSTree<LocalDateTime, Test> positiveTests;
    private final BSTree<LocalDateTime, Test> negativeTests;

    public Workplace(int id) {
        this.id = id;
        this.positiveTests = new BSTree<>();
        this.negativeTests = new BSTree<>();
    }


    public int id() {
        return id;
    }

    public void addPositivePCRTest(Test test) {
        this.positiveTests.insert(test.getDateTime(), test);
    }
    public void addNegativePCRTest(Test test) {
        this.negativeTests.insert(test.getDateTime(), test);
    }

    public BSTree<LocalDateTime, Test> getPositiveTests() {
        return positiveTests;
    }

    public BSTree<LocalDateTime, Test> getNegativeTests() {
        return negativeTests;
    }
}
