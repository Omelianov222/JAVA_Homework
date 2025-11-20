package edu.ntu.omelianov.LW_3.controller;

import edu.ntu.omelianov.LW_3.model.*;

public class Run {

    public static void main(String[] args) {
        UniversityInit initializer = new UniversityInit();
        University university = initializer.createTypicalUniversity();

        UniversityPrinter printer = new UniversityPrinter();
        printer.displayUniversityStructure(university);
    }
}
