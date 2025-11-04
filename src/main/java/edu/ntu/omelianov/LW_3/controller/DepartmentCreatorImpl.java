package edu.ntu.omelianov.LW_3.controller;


import edu.ntu.omelianov.LW_3.model.Department;
import edu.ntu.omelianov.LW_3.model.Human;

public class DepartmentCreatorImpl implements DepartmentCreator {
    private final GroupCreator groupCreator;

    public DepartmentCreatorImpl(GroupCreator groupCreator) {
        this.groupCreator = groupCreator;
    }

    @Override
    public Department createDepartment(String name, Human head) {
        return new Department(name, head);
    }

    public GroupCreator getGroupCreator() {
        return groupCreator;
    }
}