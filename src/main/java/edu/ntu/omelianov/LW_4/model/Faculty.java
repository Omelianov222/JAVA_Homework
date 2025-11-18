package edu.ntu.omelianov.LW_4.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Faculty)) return false;
        if (!super.equals(o)) return false;
        Faculty that = (Faculty) o;
        return departments.equals(that.departments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), departments);
    }

}
