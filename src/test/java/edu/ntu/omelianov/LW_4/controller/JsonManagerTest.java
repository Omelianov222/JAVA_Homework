package edu.ntu.omelianov.LW_4.controller;

import edu.ntu.omelianov.LW_4.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class JsonManagerTest {

    @Test
    public void testUniversityJsonRoundtrip() throws Exception {
        // build university with 2 faculties, each with 2 departments, each with 2 groups, each with 2 students
        Student s1 = new Student("Alice", 20, Sex.FEMALE, "s1");
        Student s2 = new Student("Bob", 21, Sex.MALE, "s2");
        Student s3 = new Student("Carol", 22, Sex.FEMALE, "s3");
        Student s4 = new Student("Dave", 23, Sex.MALE, "s4");

        Group g1 = new Group("Group1", Arrays.asList(s1, s2));
        Group g2 = new Group("Group2", Arrays.asList(s3, s4));

        Department d1 = new Department("Dept1", Arrays.asList(g1, g2));
        Department d2 = new Department("Dept2", Arrays.asList(g1, g2));

        Faculty f1 = new Faculty("Fac1", Arrays.asList(d1, d2));
        Faculty f2 = new Faculty("Fac2", Arrays.asList(d1, d2));

        University oldUniversity = new University("MyUniversity", Arrays.asList(f1, f2));

        JsonManager jm = new JsonManager();
        Path tmp = Files.createTempFile("university", ".json");
        try {
            jm.writeUniversityToFile(oldUniversity, tmp);
            University newUniversity = jm.readUniversityFromFile(tmp);
            Assert.assertEquals(oldUniversity, newUniversity);
        } finally {
            Files.deleteIfExists(tmp);
        }
    }
}
