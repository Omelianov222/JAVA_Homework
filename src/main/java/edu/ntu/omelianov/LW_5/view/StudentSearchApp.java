package edu.ntu.omelianov.LW_5.view;

import edu.ntu.omelianov.LW_5.dao.StudentDaoImpl;
import edu.ntu.omelianov.LW_3.model.Student;
import edu.ntu.omelianov.LW_5.service.StudentService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class StudentSearchApp {
    private final StudentService studentService;
    private final Scanner scanner;

    public StudentSearchApp() {
        this.studentService = new StudentService(new StudentDaoImpl());
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        try {
            studentService.initializeDatabase();
            System.out.println("База даних ініціалізована успішно!");

            boolean running = true;
            while (running) {
                displayMenu();
                int choice = getUserChoice();

                switch (choice) {
                    case 1 -> searchByMonth();
                    case 2 -> displayAllStudents();
                    case 3 -> running = false;
                    default -> System.out.println("Невірний вибір. Спробуйте ще раз.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Помилка роботи з базою даних: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private void displayMenu() {
        System.out.println("\n========== МЕНЮ ==========");
        System.out.println("1. Пошук студентів за місяцем народження");
        System.out.println("2. Показати всіх студентів");
        System.out.println("3. Вихід");
        System.out.print("Ваш вибір: ");
    }

    private int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void searchByMonth() {
        System.out.print("\nВведіть номер місяця (1-12): ");
        try {
            int month = Integer.parseInt(scanner.nextLine());
            List<Student> students = studentService.findStudentsByMonth(month);

            if (students.isEmpty()) {
                System.out.println("Студентів, які народилися в цьому місяці, не знайдено.");
            } else {
                System.out.println("\nЗнайдено студентів: " + students.size());
                displayStudents(students);
            }
        } catch (NumberFormatException e) {
            System.out.println("Помилка: введіть число від 1 до 12");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Помилка запиту до бази даних: " + e.getMessage());
        }
    }

    private void displayAllStudents() {
        try {
            List<Student> students = studentService.getAllStudents();

            if (students.isEmpty()) {
                System.out.println("База даних порожня.");
            } else {
                System.out.println("\nВсього студентів: " + students.size());
                displayStudents(students);
            }
        } catch (SQLException e) {
            System.err.println("Помилка запиту до бази даних: " + e.getMessage());
        }
    }

    private void displayStudents(List<Student> students) {
        System.out.println("\n" + "=".repeat(80));
        System.out.printf("%-20s %-15s %-15s %-12s %-15s%n",
                "Прізвище", "Ім'я", "По батькові", "Дата народж.", "№ Залікової");
        System.out.println("=".repeat(80));

        for (Student student : students) {
            System.out.printf("%-20s %-15s %-15s %-12s %-15s%n",
                    student.getLastName(),
                    student.getFirstName(),
                    student.getPatronymic(),
                    student.getBirthDate(),
                    student.getStudentId()
            );
        }
        System.out.println("=".repeat(80));
    }

    public static void main(String[] args) {
        StudentSearchApp app = new StudentSearchApp();
        app.run();
    }
}