package com.milos970.model.entity;

import com.milos970.structure.AVLTree;
import com.milos970.structure.BSTree;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Patient
{
    private final String id;
    private final String name;
    private final String surname;
    private final LocalDate birthday;
    private final BSTree<Integer, PCRTest> testsById;
    private final BSTree<LocalDateTime, PCRTest> testsByDate;

    public Patient(String id, String name, String surname, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;

        this.testsById = new AVLTree<>();
        this.testsByDate = null;
    }

    public void addTest(PCRTest test) {
        this.testsById.insert(test.id(), test);

    }

    public BSTree<Integer, PCRTest> getTestsById() {
        return testsById;
    }

    public BSTree<LocalDateTime, PCRTest> getTestsByDate() {
        return testsByDate;
    }

    public String id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }

    public LocalDate birthday() {
        return LocalDate.of(this.birthday.getYear(), this.birthday.getMonthValue(), this.birthday.getDayOfMonth());
    }

    public String surname() {
        return this.surname;
    }
}
