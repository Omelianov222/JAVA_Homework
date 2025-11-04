package edu.ntu.omelianov.LW_3.controller;

import edu.ntu.omelianov.LW_3.model.Sex;
import edu.ntu.omelianov.LW_3.model.Student;

public interface StudentCreator {
    Student createStudent(String firstName, String lastName, String patronymic, Sex sex, String studentId);
}
