package com.example.demorxgo;

import static org.junit.Assert.*;

import org.junit.Test;

public class prescriptionTest {

    @Test
    public void getId() {
        prescription a = new prescription ("a","a","a","a","a","a","a","a");
        assertEquals ( "a",a.getId () );
    }

    @Test
    public void setId() {
        prescription a = new prescription ("a","a","a","a","a","a","a","a");
        a.setId("b");
        assertEquals ( "b",a.getId () );
    }

    @Test
    public void getDrug() {
        prescription a = new prescription ("a","a","a","a","a","a","a","a");
        assertEquals ( "a",a.getDrug () );
    }

    @Test
    public void setDrug() {
        prescription a = new prescription ("a","a","a","a","a","a","a","a");
        a.setDrug("b");
        assertEquals ( "b",a.getDrug () );
    }

    @Test
    public void getRefills() {
        prescription a = new prescription ("a","a","a","a","a","a","a","a");
        assertEquals ( "a",a.getRefills () );
    }

    @Test
    public void setRefills() {
        prescription a = new prescription ("a","a","a","a","a","a","a","a");
        a.setRefills("b");
        assertEquals ( "b",a.getRefills () );
    }
}