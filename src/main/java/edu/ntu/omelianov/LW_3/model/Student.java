package edu.ntu.omelianov.LW_3.model;

import java.util.Objects;

public class Student extends Human {
    private final String studentId;
    private final String birthDate; // формат: "YYYY-MM-DD"

    public Student(String firstName, String lastName, String patronymic, Sex sex,
                   String studentId, String birthDate) {
        super(firstName, lastName, patronymic, sex);
        this.studentId = studentId;
        this.birthDate = birthDate;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getBirthDate() {
        return birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return studentId.equals(student.studentId) &&
                Objects.equals(birthDate, student.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), studentId, birthDate);
    }
}