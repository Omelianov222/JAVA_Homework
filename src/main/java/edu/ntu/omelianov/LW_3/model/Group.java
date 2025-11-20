package edu.ntu.omelianov.LW_3.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        if (!super.equals(o)) return false;
        Group that = (Group) o;
        return students.equals(that.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), students);
    }

}
