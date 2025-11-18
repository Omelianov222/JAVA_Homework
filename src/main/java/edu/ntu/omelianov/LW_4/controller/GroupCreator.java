package edu.ntu.omelianov.LW_4.controller;

import edu.ntu.omelianov.LW_4.model.Group;
import edu.ntu.omelianov.LW_4.model.Human;

public interface GroupCreator {
    Group createGroup(String name, Human head);
}
