package com.example.demorxgo;

import static org.junit.Assert.*;

import org.junit.Test;

public class chatTest {

    @Test
    public void getSender()
    {
        Chat a = new Chat("a","a","a");
        assertEquals ("a",  a.getSender() );
    }

    @Test
    public void setSender()
    {
        Chat a = new Chat("a", "a", "a");
        a.setSender("b");
        assertEquals( "b", a.getSender() );
    }

    @Test
    public void getReceiver()
    {
        Chat a = new Chat("a", "a", "a");
        assertEquals ( "a", a.getReceiver());
    }

    @Test
    public void setReceiver()
    {
        Chat a = new Chat("a", "a", "a");
        a.setReceiver("b");
        assertEquals("b", a.getReceiver());
    }

    @Test
    public void getMessage()
    {
        Chat a = new Chat ("a", "a", "a");
        assertEquals( "a", a.getMessage());
    }
}
