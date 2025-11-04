package edu.ntu.omelianov.LW_3.controller;

import edu.ntu.omelianov.LW_3.model.Sex;
import edu.ntu.omelianov.LW_3.model.Student;

public class StudentCreatorImpl implements StudentCreator {
    
    @Override
    public Student createStudent(String firstName, String lastName, String patronymic, Sex sex, String studentId) {
        return new Student(firstName, lastName, patronymic, sex, studentId);
    }
}
