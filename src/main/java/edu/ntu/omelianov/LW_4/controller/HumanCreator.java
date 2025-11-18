package edu.ntu.omelianov.LW_4.controller;

import edu.ntu.omelianov.LW_4.model.Human;
import edu.ntu.omelianov.LW_4.model.Sex;

public interface HumanCreator {
    Human createHuman(String firstName, String lastName, String patronymic, Sex sex);
}
