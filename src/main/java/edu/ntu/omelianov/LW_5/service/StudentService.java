package edu.ntu.omelianov.LW_5.service;

import edu.ntu.omelianov.LW_5.dao.StudentDao;
import edu.ntu.omelianov.LW_3.model.Student;

import java.sql.SQLException;
import java.util.List;

public class StudentService {
    private final StudentDao studentDao;

    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public void initializeDatabase() throws SQLException {
        studentDao.createTable();
    }

    public void addStudent(Student student) throws SQLException {
        studentDao.insertStudent(student);
    }

    public List<Student> findStudentsByMonth(int month) throws SQLException {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Місяць має бути від 1 до 12");
        }
        return studentDao.getStudentsByBirthMonth(month);
    }

    public List<Student> getAllStudents() throws SQLException {
        return studentDao.getAllStudents();
    }
}