package com.example.demorxgo;

public class Drug {
    String CommonUses;

    public Drug(String commonUses, String sideEffects, String drug) {
        CommonUses = commonUses;
        SideEffects = sideEffects;
        Drug = drug;
    }

    String SideEffects;
    String Drug;

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


}
