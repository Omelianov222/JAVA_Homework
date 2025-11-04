package edu.ntu.omelianov.LW_3.controller;

import edu.ntu.omelianov.LW_3.model.*;
import edu.ntu.omelianov.LW_3.controller.DepartmentCreatorImpl;
public class Run {
    
    public static void main(String[] args) {
        Run run = new Run();
        University university = run.createTypicalUniversity();
        run.displayUniversityStructure(university);
    }

    public University createTypicalUniversity() {
        UniversityCreator universityCreator = buildUniversityCreator();
        
        Human rector = createRector();
        University university = universityCreator.createUniversity("Національний технічний університет", rector);
        
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
        HumanCreator humanCreator = new HumanCreatorImpl();
        return humanCreator.createHuman("Іван", "Петренко", "Миколайович", Sex.MALE);
    }

    private Faculty createComputerScienceFaculty(UniversityCreator universityCreator) {
        UniversityCreatorImpl creatorImpl = (UniversityCreatorImpl) universityCreator;
        FacultyCreator facultyCreator = creatorImpl.getFacultyCreator();
        
        HumanCreator humanCreator = new HumanCreatorImpl();
        Human dean = humanCreator.createHuman("Олена", "Коваленко", "Петрівна", Sex.FEMALE);
        
        Faculty faculty = facultyCreator.createFaculty("Факультет комп'ютерних наук", dean);
        
        Department softwareDepartment = createSoftwareDepartment(facultyCreator);
        faculty.addDepartment(softwareDepartment);
        
        return faculty;
    }

    private Department createSoftwareDepartment(FacultyCreator facultyCreator) {
        FacultyCreatorImpl creatorImpl = (FacultyCreatorImpl) facultyCreator;
        DepartmentCreator departmentCreator = creatorImpl.getDepartmentCreator();
        
        HumanCreator humanCreator = new HumanCreatorImpl();
        Human headOfDepartment = humanCreator.createHuman("Сергій", "Мельник", "Олександрович", Sex.MALE);
        
        Department department = departmentCreator.createDepartment("Кафедра програмної інженерії", headOfDepartment);
        
        Group firstGroup = createFirstYearGroup(departmentCreator);
        department.addGroup(firstGroup);
        
        return department;
    }

    private Group createFirstYearGroup(DepartmentCreator departmentCreator) {
        DepartmentCreatorImpl creatorImpl = (DepartmentCreatorImpl) departmentCreator;
        GroupCreator groupCreator = creatorImpl.getGroupCreator();
        
        HumanCreator humanCreator = new HumanCreatorImpl();
        Human curator = humanCreator.createHuman("Марія", "Шевченко", "Василівна", Sex.FEMALE);
        
        Group group = groupCreator.createGroup("ПІ-101", curator);
        
        populateGroupWithStudents(group, groupCreator);
        
        return group;
    }

    private void populateGroupWithStudents(Group group, GroupCreator groupCreator) {
        GroupCreatorImpl creatorImpl = (GroupCreatorImpl) groupCreator;
        StudentCreator studentCreator = creatorImpl.getStudentCreator();
        
        Student firstStudent = studentCreator.createStudent("Андрій", "Ткаченко", "Іванович", Sex.MALE, "ST2024001");
        Student secondStudent = studentCreator.createStudent("Анна", "Бондаренко", "Сергіївна", Sex.FEMALE, "ST2024002");
        Student thirdStudent = studentCreator.createStudent("Дмитро", "Лисенко", "Володимирович", Sex.MALE, "ST2024003");
        
        group.addStudent(firstStudent);
        group.addStudent(secondStudent);
        group.addStudent(thirdStudent);
    }

    private void displayUniversityStructure(University university) {
        System.out.println("=== Структура університету ===");
        System.out.println("Університет: " + university.getName());
        System.out.println("Ректор: " + university.getHead().getFullName());
        
        for (Faculty faculty : university.getFaculties()) {
            displayFacultyStructure(faculty);
        }
    }

    private void displayFacultyStructure(Faculty faculty) {
        System.out.println("\n  Факультет: " + faculty.getName());
        System.out.println("  Декан: " + faculty.getHead().getFullName());
        
        for (Department department : faculty.getDepartments()) {
            displayDepartmentStructure(department);
        }
    }

    private void displayDepartmentStructure(Department department) {
        System.out.println("\n    Кафедра: " + department.getName());
        System.out.println("    Завідувач: " + department.getHead().getFullName());
        
        for (Group group : department.getGroups()) {
            displayGroupStructure(group);
        }
    }

    private void displayGroupStructure(Group group) {
        System.out.println("\n      Група: " + group.getName());
        System.out.println("      Куратор: " + group.getHead().getFullName());
        System.out.println("      Студенти:");
        
        for (Student student : group.getStudents()) {
            displayStudentInfo(student);
        }
    }

    private void displayStudentInfo(Student student) {
        System.out.println("        - " + student.getFullName() + " (ID: " + student.getStudentId() + ")");
    }
}
