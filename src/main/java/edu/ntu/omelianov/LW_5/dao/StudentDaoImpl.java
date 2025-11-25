package edu.ntu.omelianov.LW_5.dao;

import edu.ntu.omelianov.LW_3.model.Sex;
import edu.ntu.omelianov.LW_3.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DatabaseConfig.getUrl(),
                DatabaseConfig.getUser(),
                DatabaseConfig.getPassword()
        );
    }

    @Override
    public void createTable() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS students (
                id SERIAL PRIMARY KEY,
                last_name VARCHAR(100) NOT NULL,
                first_name VARCHAR(100) NOT NULL,
                patronymic VARCHAR(100) NOT NULL,
                birth_date VARCHAR(10) NOT NULL,
                student_id VARCHAR(50) UNIQUE NOT NULL,
                sex VARCHAR(10) NOT NULL
            )
            """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    @Override
    public void insertStudent(Student student) throws SQLException {
        String sql = """
            INSERT INTO students (last_name, first_name, patronymic, birth_date, student_id, sex)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getLastName());
            pstmt.setString(2, student.getFirstName());
            pstmt.setString(3, student.getPatronymic());
            pstmt.setString(4, student.getBirthDate());
            pstmt.setString(5, student.getStudentId());
            pstmt.setString(6, student.getSex().name());
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<Student> getStudentsByBirthMonth(int month) throws SQLException {
        String sql = """
            SELECT * FROM students 
            WHERE CAST(SUBSTRING(birth_date, 6, 2) AS INTEGER) = ?
            ORDER BY last_name, first_name
            """;

        List<Student> students = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, month);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    students.add(mapResultSetToStudent(rs));
                }
            }
        }

        return students;
    }

    @Override
    public List<Student> getAllStudents() throws SQLException {
        String sql = "SELECT * FROM students ORDER BY last_name, first_name";
        List<Student> students = new ArrayList<>();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                students.add(mapResultSetToStudent(rs));
            }
        }

        return students;
    }

    private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
        return new Student(
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("patronymic"),
                Sex.valueOf(rs.getString("sex")),
                rs.getString("student_id"),
                rs.getString("birth_date")
        );
    }
}