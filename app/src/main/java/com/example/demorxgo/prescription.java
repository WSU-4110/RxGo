package com.example.demorxgo;

public class prescription {

    private String id;
    private String drug;
    private String strength;
    private String refills;

    public prescription(String id, String drug, String strength,String refills) {
        this.id = id;
        this.drug = drug;
        this.strength = strength;
        this.refills = refills;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getRefills() {
        return refills;
    }

    public void setRefills(String refills) {
        this.refills = refills;
    }

    @Override
    public String toString() {
        return "prescription{" +
                "id=" + id +
                ", drug='" + drug + '\'' +
                ", strength='" + strength + '\'' +
                '}';
    }
}
