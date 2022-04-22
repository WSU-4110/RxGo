package com.example.demorxgo;

public class Drug {

    String CommonUses;
    String SideEffects;
    String Drug;

    public Drug(String CommonUses, String SideEffects, String Drug) {
        CommonUses = CommonUses;
        SideEffects = SideEffects;
        Drug = Drug;
    }


    public String getCommonUses() {
        return CommonUses;
    }

    public void setCommonUses(String commonUses) {
        CommonUses = commonUses;
    }

    public String getSideEffects() {
        return SideEffects;
    }

    public void setSideEffects(String sideEffects) {
        SideEffects = sideEffects;
    }

    public String getDrug() {
        return Drug;
    }

    public void setDrug(String drug) {
        Drug = drug;
    }

    public boolean equals(String x)
    {
        return this.getDrug () == x;
    }


}
