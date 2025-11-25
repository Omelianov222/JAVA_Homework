package edu.ntu.omelianov.LW_5.dao;

import edu.ntu.omelianov.LW_3.controller.UniversityInit;
import edu.ntu.omelianov.LW_5.dao.StudentDaoImpl;
import edu.ntu.omelianov.LW_3.model.*;
import edu.ntu.omelianov.LW_5.service.StudentService;

import java.sql.SQLException;

public class DatabasePopulator {

    public static void main(String[] args) {
        try {
            StudentService service = new StudentService(new StudentDaoImpl());
            service.initializeDatabase();

            // Створюємо університет та отримуємо студентів
            UniversityInit init = new UniversityInit();
            University university = init.createTypicalUniversity();

            // Додаємо студентів до БД
            for (Faculty faculty : university.getFaculties()) {
                for (Department department : faculty.getDepartments()) {
                    for (Group group : department.getGroups()) {
                        for (Student student : group.getStudents()) {
                            service.addStudent(student);
                            System.out.println("Додано студента: " + student.getFullName());
                        }
                    }
                }
            }

            System.out.println("\nБазу даних успішно наповнено!");

        } catch (SQLException e) {
            System.err.println("Помилка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}