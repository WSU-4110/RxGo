package com.example.demorxgo;

public class doctors {

    private String firstName;
    private String lastName;
    private String NPI;
    private String id;

    public doctors(String firstName, String lastName, String npi, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        NPI = npi;
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

    public String getNPI() {
        return NPI;
    }

    public void setNPI(String npi) {
        this.NPI = npi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "doctor{" +
                "id=" + id +
                ", name='" + firstName + '\'' +
                '}';
    }


}
