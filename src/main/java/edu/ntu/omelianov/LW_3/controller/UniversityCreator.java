package edu.ntu.omelianov.LW_3.controller;

import edu.ntu.omelianov.LW_3.model.Human;
import edu.ntu.omelianov.LW_3.model.University;

public interface UniversityCreator {
    University createUniversity(String name, Human head);
}
