package edu.ntu.omelianov.LW_4.controller;

import edu.ntu.omelianov.LW_4.model.Human;
import edu.ntu.omelianov.LW_4.model.University;

public class UniversityCreatorImpl implements UniversityCreator {
    private final FacultyCreator facultyCreator;

    public UniversityCreatorImpl(FacultyCreator facultyCreator) {
        this.facultyCreator = facultyCreator;
    }

    @Override
    public University createUniversity(String name, Human head) {
        return new University(name, head);
    }

    public FacultyCreator getFacultyCreator() {
        return facultyCreator;
    }
}
