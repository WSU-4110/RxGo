package com.example.demorxgo;
import static org.junit.Assert.*;

import org.junit.Test;


public class DrugTest {

    @Test
    public void getCommonUses() {
        Drug a = new Drug ("a", "a", "a");
        assertEquals ( "a", a.getCommonUses () );
    }

    @Test
    public void setCommonUses() {
        Drug a = new Drug ("a", "a", "a");
        a.setCommonUses("b");
        assertEquals ( "b", a.getCommonUses () );
    }

    @Test
    public void setSideEffects() {
        Drug a = new Drug ("a","a", "a");
        a.setSideEffects("b");
        assertEquals ( "b", a.getSideEffects () );
    }

    @Test
    public void getSideEffects() {
        Drug a = new Drug ("a", "a", "a");
        assertEquals ( "a",a.getSideEffects () );
    }

    @Test
    public void setDrug() {
        Drug a = new Drug ("a", "a", "a");
        a.setDrug("b");
        assertEquals ( "b", a.getDrug () );
    }

    @Test
    public void getDrug() {
        Drug a = new Drug ("a", "a", "a");
        assertEquals ( "a",a.getDrug () );
    }
}
