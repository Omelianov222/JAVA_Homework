package edu.ntu.omelianov.LW_5.dao;

import edu.ntu.omelianov.LW_3.model.Student;
import java.sql.SQLException;
import java.util.List;

public interface StudentDao {
    void createTable() throws SQLException;
    void insertStudent(Student student) throws SQLException;
    List<Student> getStudentsByBirthMonth(int month) throws SQLException;
    List<Student> getAllStudents() throws SQLException;
}