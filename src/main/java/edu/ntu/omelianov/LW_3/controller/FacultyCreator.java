package edu.ntu.omelianov.LW_3.controller;

import edu.ntu.omelianov.LW_3.model.Faculty;
import edu.ntu.omelianov.LW_3.model.Human;

public interface FacultyCreator {
    Faculty createFaculty(String name, Human head);
}
