package com.example.demorxgo;

public class refillrequest {
    String ptNamef;
    String PtNamel;
    String Drug;
    String Birth;

    public refillrequest(  String ptNamef,
            String PtNamel,
            String Drug,
            String Birth){
        this.Birth=Birth;
        this.Drug=Drug;
        this.ptNamef=ptNamef;
        this.PtNamel=PtNamel;

    }

    public String getPtNamef() {
        return ptNamef;
    }

    public void setPtNamef(String ptNamel) {
        this.ptNamef = ptNamef;
    }

    public String getDrug() {
        return Drug;
    }

    public void setDrug(String drug) {
        this.Drug = drug;
    }

    public String getBirth()
    {
        return Birth;
    }

    public void setBirth(String strength) {
        this.Birth = strength;
    }

    public String getPtNamel() {
        return PtNamel;
    }

    public void setPtNamel(String refills) {
        this.PtNamel = refills;
    }


}
