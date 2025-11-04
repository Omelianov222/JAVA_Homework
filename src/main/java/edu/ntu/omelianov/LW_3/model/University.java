package edu.ntu.omelianov.LW_3.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
}
