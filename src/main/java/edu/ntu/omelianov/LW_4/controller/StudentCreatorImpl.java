package edu.ntu.omelianov.LW_4.controller;

import edu.ntu.omelianov.LW_4.model.Sex;
import edu.ntu.omelianov.LW_4.model.Student;

public class StudentCreatorImpl implements StudentCreator {
    
    @Override
    public Student createStudent(String firstName, String lastName, String patronymic, Sex sex, String studentId) {
        return new Student(firstName, lastName, patronymic, sex, studentId);
    }
}
