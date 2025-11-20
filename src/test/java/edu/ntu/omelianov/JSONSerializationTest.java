package edu.ntu.omelianov;

import edu.ntu.omelianov.LW_3.model.*;
import edu.ntu.omelianov.LW_3.controller.*;
import edu.ntu.omelianov.LW_4.JSONController;
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
        Human rector = new Human("John", "Smith", "A", Sex.MALE);
        University university = new University("Test University", rector);

        for (int i = 1; i <= 2; i++) {
            Faculty faculty = new Faculty("Faculty " + i,
                    new Human("Dean", "F" + i, "A", Sex.MALE));

            for (int j = 1; j <= 2; j++) {
                Department department = new Department("Department " + i + "." + j,
                        new Human("Head", "D" + i + j, "A", Sex.FEMALE));

                for (int g = 1; g <= 2; g++) {
                    Group group = new Group("Group " + i + "." + j + "." + g,
                            new Human("Curator", "G" + i + j + g, "A", Sex.FEMALE));

                    group.addStudent(new Student("Student", "S" + g + "1", "A", Sex.MALE, "ID" + i + j + g + "1"));
                    group.addStudent(new Student("Student", "S" + g + "2", "A", Sex.FEMALE, "ID" + i + j + g + "2"));

                    department.addGroup(group);
                }
                faculty.addDepartment(department);
            }
            university.addFaculty(faculty);
        }

        return university;
    }
}
