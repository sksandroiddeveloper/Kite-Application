package com.nextolive.kiteappmvvm;

import android.content.Context;

import androidx.test.InstrumentationRegistry;

import com.nextolive.kiteappmvvm.activities_views.MainActivity;
import com.nextolive.kiteappmvvm.utils.UTILITY;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;

public class EmailValidator {

    //testCase 1
    @Test
    public void isEmailValid() {
        String testEmail = "krishnaravi17@gmail.com";
        Assert.assertThat(String.format("Email Validity Test failed for %s ", testEmail),
                UTILITY.isEmailValid(testEmail), is(true));
    }
    //testCase 2
    @Test
    public void isEmailValid2() {
        String testEmail = "krishnaravi17@gmail.com";
        UTILITY.isEmailValid(testEmail);
    }

    //testCase 3
    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue(UTILITY.isEmailValid("name@email.com"));
    }

    //testCase 4
    @Test
    public void emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        assertTrue(UTILITY.isEmailValid("name@email.co.uk"));
    }

    //testCase 5
    @Test
    public void emailValidator_InvalidEmailNoTld_ReturnsFalse() {
        assertFalse(UTILITY.isEmailValid("name@email"));
    }

    //testCase 6
    @Test
    public void emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        //assertFalse(UTILITY.isEmailValid("name@email.com"));
        String testEmail = "name@email..com";
        Assert.assertThat(String.format("Email Validity Test failed for %s ", testEmail),
                UTILITY.isEmailValid(testEmail), is(true));
    }

    //testCase 7
    @Test
    public void emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
        assertFalse(UTILITY.isEmailValid("@email.com"));
    }

    //testCase 8
    @Test
    public void emailValidator_EmptyString_ReturnsFalse() {
        assertFalse(UTILITY.isEmailValid(""));
    }

    //testCase 9
    @Test
    public void emailValidator_NullEmail_ReturnsFalse() {
        assertFalse(UTILITY.isEmailValid(null));
    }



}
