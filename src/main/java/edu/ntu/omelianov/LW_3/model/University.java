package edu.ntu.omelianov.LW_3.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class University extends StructuralUnit {
    private final List<Faculty> faculties;

    public University(String name, Human head) {
        super(name, head);
        this.faculties = new ArrayList<>();
    }

    public void addFaculty(Faculty faculty) {
        faculties.add(faculty);
    }

    public List<Faculty> getFaculties() {
        return Collections.unmodifiableList(faculties);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof University)) return false;
        if (!super.equals(o)) return false;
        University that = (University) o;
        return faculties.equals(that.faculties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), faculties);
    }

}
