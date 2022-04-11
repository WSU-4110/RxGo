package com.example.demorxgo;
import junit.framework.TestCase;


public class DrugTest extends TestCase{


    public void testSetCommonUses() {
        Drug uses = new Drug ("uses");
        uses.setCommonUses("Common Uses");
        assertEquals ( "Common Uses", uses.getCommonUses () );
    }


    public void testGetCommonUses() {
        Drug SideEffects = new Drug ("uses");
        assertEquals ( "uses",SideEffects.getCommonUses () );
    }

    public void testSetSideEffects() {
        Drug SideEffects = new Drug ("SideEffects");
        SideEffects.setCommonUses("Side Effects");
        assertEquals ( "Side Effects", SideEffects.getCommonUses () );
    }

    public void testGetSideEffects() {
        Drug SideEffects = new Drug ("SideEffects");
        assertEquals ( "SideEffects",SideEffects.getCommonUses () );
    }

    public void testSetDrug() {
        Drug drug = new Drug ("drug");
        drug.setCommonUses("drug");
        assertEquals ( "drug", drug.getCommonUses () );
    }

    public void tesGetDrug() {
        Drug drug = new Drug ("drug");
        assertEquals ( "drug",drug.getCommonUses () );
    }
}

