package edu.ntu.omelianov.LW_4.controller;

import edu.ntu.omelianov.LW_4.model.Human;
import edu.ntu.omelianov.LW_4.model.University;

public interface UniversityCreator {
    University createUniversity(String name, Human head);
}
