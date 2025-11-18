package edu.ntu.omelianov;

import edu.ntu.omelianov.LW_4.controller.JSONController;
import edu.ntu.omelianov.LW_4.model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JSONSerializationTest {

    @Test
    public void testUniversityJsonSerialization() throws IOException {
        University oldUniversity = buildUniversity();

        JSONController jsonController = new JSONController();
        String filePath = "test_university.json";

        jsonController.writeToFile(oldUniversity, filePath);
        University newUniversity = jsonController.readFromFile(filePath);

        assertEquals(oldUniversity, newUniversity);
    }

    private University buildUniversity() {
        Human rector = new Human("Іван", "Петренко", "Миколайович", Sex.MALE);
        University university = new University("Тестовий Університет", rector);

        for (int i = 1; i <= 2; i++) {
            Faculty faculty = new Faculty("Факультет " + i,
                    new Human("Декан", "Ф" + i, "П", Sex.MALE));

            for (int j = 1; j <= 2; j++) {
                Department department = new Department("Кафедра " + i + "." + j,
                        new Human("Завідувач", "К" + i + j, "П", Sex.FEMALE));

                for (int g = 1; g <= 2; g++) {
                    Group group = new Group("Група " + i + "." + j + "." + g,
                            new Human("Куратор", "Г" + i + j + g, "П", Sex.FEMALE));

                    group.addStudent(new Student("Студент", "S" + g + "1", "П", Sex.MALE, "ID" + i + j + g + "1"));
                    group.addStudent(new Student("Студент", "S" + g + "2", "П", Sex.FEMALE, "ID" + i + j + g + "2"));

                    department.addGroup(group);
                }
                faculty.addDepartment(department);
            }
            university.addFaculty(faculty);
        }

        return university;
    }
}
