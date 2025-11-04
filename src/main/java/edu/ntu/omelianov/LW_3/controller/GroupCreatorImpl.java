package edu.ntu.omelianov.LW_3.controller;

import edu.ntu.omelianov.LW_3.model.Group;
import edu.ntu.omelianov.LW_3.model.Human;

public class GroupCreatorImpl implements GroupCreator {
    private final StudentCreator studentCreator;

    public GroupCreatorImpl(StudentCreator studentCreator) {
        this.studentCreator = studentCreator;
    }

    @Override
    public Group createGroup(String name, Human head) {
        return new Group(name, head);
    }

    public StudentCreator getStudentCreator() {
        return studentCreator;
    }
}
