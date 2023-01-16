package com.company.model;

import com.company.enums.UserRole;
import com.company.model.base.BaseModel;

import java.time.LocalDate;

public class User extends BaseModel {
    private String firstName;
    private String lastName;
    private String passport; // unique
    private LocalDate birthDate;

    public User(String firstName, String lastName, String passport, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.passport = passport;
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", passport='" + passport + '\'' +
                ", birthDate=" + birthDate +
                ", deleted=" + deleted +
                '}';
    }
}
