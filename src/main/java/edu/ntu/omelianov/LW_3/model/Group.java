package edu.ntu.omelianov.LW_3.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group extends StructuralUnit {
    private final List<Student> students;

    public Group(String name, Human head) {
        super(name, head);
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public List<Student> getStudents() {
        return Collections.unmodifiableList(students);
    }
}
