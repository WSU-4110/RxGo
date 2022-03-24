package com.example.demorxgo;

public class patients {
    private String firstName;
    private String lastName;
    private String birthday;
    private String id;


    public patients(String firstName, String lastName, String birthday, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.id = id;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "patient{" +
                "id=" + id +
                ", name='" + firstName + '\'' +
                '}';
    }
}
