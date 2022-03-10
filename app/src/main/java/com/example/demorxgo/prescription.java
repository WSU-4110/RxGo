package com.example.demorxgo;

public class prescription{

    private String id;
    private String drug;
    private String strength;

    public prescription(String id, String drug, String strength) {
        builder ( id,drug,strength );
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

    @Override
    public String toString() {
        return "prescription{" +
                "id=" + id +
                ", drug='" + drug + '\'' +
                ", strength='" + strength + '\'' +
                '}';
    }

    public void builder(String id,String name, String strth){
        this.id = id;
        this.drug = name;
        this.strength = strth;
    }

}
