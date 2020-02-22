package com.keaz.tool.apibuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keaz.tool.apibuilder.apiobject.ApiDefinition;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileReaderTest {


    @Mock
    private ObjectMapper objectMapper;


    @InjectMocks
    private FileReader fileReader;

    @Before
    public void beforeTests() {
    }

    @After
    public void afterTest() {

    }


    @Test(expected = IllegalArgumentException.class)
    public void testReadJsonJsonFileNull() {
        fileReader.readFile(null);
    }

    @Test
    public void testReadJson() throws IOException {
        String jsonFile = "{}";
        ApiDefinition expected = new ApiDefinition();
        when(objectMapper.readValue(new File(jsonFile), ApiDefinition.class)).thenReturn(expected);
        ApiDefinition actual = fileReader.readFile(jsonFile);

        Assert.assertEquals(expected, actual);
    }

}
