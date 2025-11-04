package edu.ntu.omelianov.LW_3.controller;

import edu.ntu.omelianov.LW_3.model.Human;
import edu.ntu.omelianov.LW_3.model.Sex;

public class HumanCreatorImpl implements HumanCreator {
    
    @Override
    public Human createHuman(String firstName, String lastName, String patronymic, Sex sex) {
        return new Human(firstName, lastName, patronymic, sex);
    }
}
