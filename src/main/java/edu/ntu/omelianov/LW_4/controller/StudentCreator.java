package edu.ntu.omelianov.LW_4.controller;

import edu.ntu.omelianov.LW_4.model.Sex;
import edu.ntu.omelianov.LW_4.model.Student;

public interface StudentCreator {
    Student createStudent(String firstName, String lastName, String patronymic, Sex sex, String studentId);
}
