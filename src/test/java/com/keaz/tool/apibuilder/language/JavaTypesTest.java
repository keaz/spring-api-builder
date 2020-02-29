package com.keaz.tool.apibuilder.language;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JavaTypesTest {


    private ObjectMapper objectMapper;


    private JavaTypes javaTypes;

    @Before
    public void beforeTests() {
        objectMapper =  new ObjectMapper();
        javaTypes = new JavaTypes(objectMapper);
    }

    @After
    public void afterTest() {

    }


    @Test
    public void testInit(){
        javaTypes.init();
    }

    @Test
    public void testGetJavaDefaultType(){
        javaTypes.init();
        String actual = javaTypes.getType("string", null);
        Assert.assertEquals("String",actual);
    }

    @Test
    public void testGetJavaType(){
        javaTypes.init();
        String actual = javaTypes.getType("string", "date-time");
        Assert.assertEquals("Date",actual);
    }


}
