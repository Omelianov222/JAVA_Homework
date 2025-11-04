package edu.ntu.omelianov.LW_3.controller;

import edu.ntu.omelianov.LW_3.model.Human;
import edu.ntu.omelianov.LW_3.model.Sex;

public interface HumanCreator {
    Human createHuman(String firstName, String lastName, String patronymic, Sex sex);
}
