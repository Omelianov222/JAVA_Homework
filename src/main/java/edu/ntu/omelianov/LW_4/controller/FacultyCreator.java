package edu.ntu.omelianov.LW_4.controller;

import edu.ntu.omelianov.LW_4.model.Faculty;
import edu.ntu.omelianov.LW_4.model.Human;

public interface FacultyCreator {
    Faculty createFaculty(String name, Human head);
}
