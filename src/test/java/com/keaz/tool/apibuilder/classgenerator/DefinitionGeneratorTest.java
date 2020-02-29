package com.keaz.tool.apibuilder.classgenerator;

import com.keaz.tool.apibuilder.apiobject.Attribute;
import com.keaz.tool.apibuilder.apiobject.Resource;
import com.keaz.tool.apibuilder.classgenerator.java.spring.DefinitionGenerator;
import org.ainslec.picocog.PicoWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class DefinitionGeneratorTest {

    @Spy
    PicoWriter topWriter; // createDeferredWriter is final we cannot mock that
    @InjectMocks
    private DefinitionGenerator instance;
    private Resource resource;

    private File resourcePackage;

    @Before
    public void beforeTests() {
        resourcePackage = new File("test");
        resourcePackage.mkdirs();

        resource = new Resource();
        resource.setPackageName("test");
        resource.setName("Test");
        Attribute attribute = new Attribute();
        attribute.setName("test");
        attribute.setType("String");

        resource.setAttributes(Collections.singleton(attribute));
    }

    @After
    public void afterTest() {

        new File(resourcePackage, "Test.java").delete();
        resourcePackage.delete();
    }

    @Test
    public void testGenerate() {

//        when(topWriter.writeln_r("")).thenReturn(new PicoWriter());
//        when(topWriter.writeln_l("")).thenReturn(new PicoWriter());
//        lenient().when(topWriter.writeln("")).thenReturn(new PicoWriter());
//        doReturn(new PicoWriter()).when(topWriter).writeln("");
//        doReturn(topWriter).when(topWriter).createDeferredWriter();
        instance.generate(resourcePackage, resource, topWriter);

    }

}
