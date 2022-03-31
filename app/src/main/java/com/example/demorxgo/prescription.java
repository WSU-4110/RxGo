package com.example.demorxgo;

import android.app.AlertDialog;


public class prescription {

    private String id; private String drug;
    private String strength; private String refills;
    private String dr; private String dPhone;
    private String date; private String sig;

    public prescription(String id, String drug, String strength, String refills, String dr, String dPhone, String date, String sig) {
        this.id = id;
        this.drug = drug;
        this.strength = strength;
        this.refills = refills;
        this.dr=dr;
        this.dPhone=dPhone;
        this.date=date;
        this.sig = sig;
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

    public void decrementRefill()
    {
        int refillN =Integer.parseInt (refills);
        if(refillN>0){
            refillN--;
            refills=String.valueOf ( refillN );
        }
        else
        {
            System.out.println ("request refill");
        }

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getDr() {
        return dr;
    }

    public void setDr(String dr) {
        this.dr = dr;
    }

    public String getdPhone() {
        return dPhone;
    }

    public void setdPhone(String dPhone) {
        this.dPhone = dPhone;
    }

    @Override
    public String toString() {
        return "prescription{" +
                "id=" + id +
                ", drug='" + drug + '\'' +
                ", strength='" + strength + '\'' +
                '}';
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }
}
