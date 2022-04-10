package com.example.demorxgo;

import static org.junit.jupiter.api.Assertions.*;

class prescriptionTest {

    @org.junit.jupiter.api.Test
    void getId() {
        prescription a = new prescription ("a","a","a","a","a","a","a","a");
        assertEquals ( "a",a.getId () );
    }

    @org.junit.jupiter.api.Test
    void setId() {
        prescription a = new prescription ("a","a","a","a","a","a","a","a");
        a.setId("b");
        assertEquals ( "b",a.getId () );

    }

    @org.junit.jupiter.api.Test
    void getDrug() {
        prescription a = new prescription ("a","a","a","a","a","a","a","a");
        assertEquals ( "a",a.getDrug () );
    }

    @org.junit.jupiter.api.Test
    void setDrug() {
        prescription a = new prescription ("a","a","a","a","a","a","a","a");
        a.setDrug("b");
        assertEquals ( "b",a.getDrug() );
    }

    @org.junit.jupiter.api.Test
    void getRefills() {
        prescription a = new prescription ("a","a","a","a","a","a","a","a");
        assertEquals ( "a",a.getRefills () );
    }

    @org.junit.jupiter.api.Test
    void setRefills() {
        prescription a = new prescription ("a","a","a","a","a","a","a","a");
        a.setRefills ("b");
        assertEquals ( "b",a.getRefills () );
    }
}