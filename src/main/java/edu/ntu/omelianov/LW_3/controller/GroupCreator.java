package edu.ntu.omelianov.LW_3.controller;

import edu.ntu.omelianov.LW_3.model.Group;
import edu.ntu.omelianov.LW_3.model.Human;

public interface GroupCreator {
    Group createGroup(String name, Human head);
}
