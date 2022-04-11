package com.example.demorxgo;

import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;

import junit.framework.TestCase;

public class RegisterTest extends TestCase {

    public void testOnCreate() {

        String email = null;
        if (email.isEmpty()) {
           System.out.println("Email is required.");
        }
        String password= "12545";
        if (TextUtils.isEmpty(password)) {
            System.out.println("Password is required.");
        }
        if (password.length() < 6) {
            System.out.println("Password must be greater or equals to 6 characters.");
        }
    }
}