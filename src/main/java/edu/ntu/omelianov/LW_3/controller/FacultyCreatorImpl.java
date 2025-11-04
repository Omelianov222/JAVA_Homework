package edu.ntu.omelianov.LW_3.controller;

import edu.ntu.omelianov.LW_3.model.Faculty;
import edu.ntu.omelianov.LW_3.model.Human;

public class FacultyCreatorImpl implements FacultyCreator {
    private final DepartmentCreator departmentCreator;

    public FacultyCreatorImpl(DepartmentCreator departmentCreator) {
        this.departmentCreator = departmentCreator;
    }

    @Override
    public Faculty createFaculty(String name, Human head) {
        return new Faculty(name, head);
    }

    public DepartmentCreator getDepartmentCreator() {
        return departmentCreator;
    }
}
