package edu.ntu.omelianov.LW_3.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Faculty extends StructuralUnit {
    private final List<Department> departments;

    public Faculty(String name, Human head) {
        super(name, head);
        this.departments = new ArrayList<>();
    }

    public void addDepartment(Department department) {
        departments.add(department);
    }

    public List<Department> getDepartments() {
        return Collections.unmodifiableList(departments);
    }
}
