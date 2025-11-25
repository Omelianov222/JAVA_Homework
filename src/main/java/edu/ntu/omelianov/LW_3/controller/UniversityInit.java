package edu.ntu.omelianov.LW_3.controller;

import edu.ntu.omelianov.LW_3.model.*;

public class UniversityInit {

    public University createTypicalUniversity() {
        UniversityCreator universityCreator = buildUniversityCreator();

        Human rector = createRector();
        University university = universityCreator.createUniversity(
                "National Technical University",
                rector
        );

        Faculty computerScienceFaculty = createComputerScienceFaculty(universityCreator);
        university.addFaculty(computerScienceFaculty);

        return university;
    }

    private UniversityCreator buildUniversityCreator() {
        StudentCreator studentCreator = new StudentCreatorImpl();
        GroupCreator groupCreator = new GroupCreatorImpl(studentCreator);
        DepartmentCreator departmentCreator = new DepartmentCreatorImpl(groupCreator);
        FacultyCreator facultyCreator = new FacultyCreatorImpl(departmentCreator);
        return new UniversityCreatorImpl(facultyCreator);
    }

    private Human createRector() {
        HumanCreator creator = new HumanCreatorImpl();
        return creator.createHuman("John", "Smith", "A", Sex.MALE);
    }

    private Faculty createComputerScienceFaculty(UniversityCreator creator) {
        UniversityCreatorImpl impl = (UniversityCreatorImpl) creator;
        FacultyCreator facultyCreator = impl.getFacultyCreator();

        HumanCreator humanCreator = new HumanCreatorImpl();
        Human dean = humanCreator.createHuman("Alice", "Brown", "B", Sex.FEMALE);

        Faculty faculty = facultyCreator.createFaculty("Computer Science Faculty", dean);

        Department softwareDepartment = createSoftwareDepartment(facultyCreator);
        faculty.addDepartment(softwareDepartment);

        return faculty;
    }

    private Department createSoftwareDepartment(FacultyCreator facultyCreator) {
        FacultyCreatorImpl impl = (FacultyCreatorImpl) facultyCreator;
        DepartmentCreator departmentCreator = impl.getDepartmentCreator();

        HumanCreator humanCreator = new HumanCreatorImpl();
        Human head = humanCreator.createHuman("Robert", "Johnson", "C", Sex.MALE);

        Department department = departmentCreator.createDepartment(
                "Software Engineering Department",
                head
        );

        Group group = createFirstYearGroup(departmentCreator);
        department.addGroup(group);

        return department;
    }

    private Group createFirstYearGroup(DepartmentCreator departmentCreator) {
        DepartmentCreatorImpl impl = (DepartmentCreatorImpl) departmentCreator;
        GroupCreator groupCreator = impl.getGroupCreator();

        HumanCreator humanCreator = new HumanCreatorImpl();
        Human curator = humanCreator.createHuman("Maria", "Green", "D", Sex.FEMALE);

        Group group = groupCreator.createGroup("CS-101", curator);

        populateGroupWithStudents(group, groupCreator);

        return group;
    }

    private void populateGroupWithStudents(Group group, GroupCreator groupCreator) {
        GroupCreatorImpl impl = (GroupCreatorImpl) groupCreator;
        StudentCreator studentCreator = impl.getStudentCreator();

        group.addStudent(studentCreator.createStudent("Tom", "Walker", "E", Sex.MALE, "ST2024001", "2005-03-22"));
        group.addStudent(studentCreator.createStudent("Emma", "Clark", "F", Sex.FEMALE, "ST2024002", "2005-05-22"));
        group.addStudent(studentCreator.createStudent("David", "Lewis", "G", Sex.MALE, "ST2024003", "2005-04-22"));
    }
}
