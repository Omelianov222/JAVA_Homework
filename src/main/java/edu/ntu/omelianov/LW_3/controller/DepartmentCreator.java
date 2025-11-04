package edu.ntu.omelianov.LW_3.controller;

import edu.ntu.omelianov.LW_3.model.Department;
import edu.ntu.omelianov.LW_3.model.Human;


public interface DepartmentCreator {
    Department createDepartment(String name, Human head);
}
