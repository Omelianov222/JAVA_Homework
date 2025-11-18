package edu.ntu.omelianov.LW_4.model;
import java.util.Objects;

public class Student extends Human {
    private final String studentId;

    public Student(String firstName, String lastName, String patronymic, Sex sex, String studentId) {
        super(firstName, lastName, patronymic, sex);
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return studentId.equals(student.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), studentId);
    }

}
