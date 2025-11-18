package edu.ntu.omelianov.LW_4.controller;

import edu.ntu.omelianov.LW_4.model.Department;
import edu.ntu.omelianov.LW_4.model.Human;


public interface DepartmentCreator {
    Department createDepartment(String name, Human head);
}
