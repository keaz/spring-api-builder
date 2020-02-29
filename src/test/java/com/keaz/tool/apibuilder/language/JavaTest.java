package com.keaz.tool.apibuilder.language;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JavaTest {


    private ObjectMapper objectMapper;


    private Java java;

    @Before
    public void beforeTests() {
        objectMapper = new ObjectMapper();
        java = new Java(objectMapper);
    }

    @After
    public void afterTest() {

    }


    @Test
    public void testInit() {
        java.init();
    }

    @Test
    public void testGetJavaDefaultType() {
        java.init();
        String actual = java.getType("string", null);
        Assert.assertEquals("String", actual);
    }

    @Test
    public void testGetJavaType() {
        java.init();
        String actual = java.getType("string", "date-time");
        Assert.assertEquals("Date", actual);
    }


}
