package edu.ntu.omelianov.LW_3.controller;

import edu.ntu.omelianov.LW_3.model.*;

public class UniversityPrinter {

    public void displayUniversityStructure(University university) {
        System.out.println("University Structure ");
        System.out.println("University: " + university.getName());
        System.out.println("Rector: " + university.getHead().getFullName());

        for (Faculty faculty : university.getFaculties()) {
            displayFaculty(faculty);
        }
    }

    private void displayFaculty(Faculty faculty) {
        System.out.println("\n  Faculty: " + faculty.getName());
        System.out.println("  Dean: " + faculty.getHead().getFullName());

        for (Department department : faculty.getDepartments()) {
            displayDepartment(department);
        }
    }

    private void displayDepartment(Department department) {
        System.out.println("\n    Department: " + department.getName());
        System.out.println("    Head: " + department.getHead().getFullName());

        for (Group group : department.getGroups()) {
            displayGroup(group);
        }
    }

    private void displayGroup(Group group) {
        System.out.println("\n      Group: " + group.getName());
        System.out.println("      Curator: " + group.getHead().getFullName());
        System.out.println("      Students:");

        for (Student student : group.getStudents()) {
            System.out.println("        - " + student.getFullName() +
                    " (ID: " + student.getStudentId() + ")");
        }
    }
}
