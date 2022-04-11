package com.example.demorxgo;

import static org.junit.Assert.*;
import org.junit.Test;

public class messageTest {

    @Test
    public void getMessageText() {
        Messages a = new Messages ("a","a");
        assertEquals ( "a",a.getMessageText() );
    }

    @Test
    public void setMessageText() {
        Messages a = new Messages("a", "a");
        a.setMessageText("b");
        assertEquals("b", a.getMessageText());
    }

    @Test
    public void getMessageUser() {
        Messages a = new Messages("a", "a");
        assertEquals("a", a.getMessageUser());

    }

    @Test
    public void setMessageUser() {
        Messages a = new Messages("a", "a");
        a.setMessageUser("b");
        assertEquals("b", a.getMessageUser());
    }

    @Test
    public void getMessageTime() {
        Messages a = new Messages("a", "a");
        assertEquals("a", a.getMessageTime());
    }

    @Test
    public void setMessageTime() {
        Messages a = new Messages("a", "a");
        a.setMessageTime(120000);
        assertEquals(120000, a.getMessageTime());
    }
}
