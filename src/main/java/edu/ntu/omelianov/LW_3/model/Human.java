package edu.ntu.omelianov.LW_3.model;

public class Human {
    private final String firstName;
    private final String lastName;
    private final String patronymic;
    private final Sex sex;

    public Human(String firstName, String lastName, String patronymic, Sex sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.sex = sex;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public Sex getSex() {
        return sex;
    }

    public String getFullName() {
        return lastName + " " + firstName + " " + patronymic;
    }
}
