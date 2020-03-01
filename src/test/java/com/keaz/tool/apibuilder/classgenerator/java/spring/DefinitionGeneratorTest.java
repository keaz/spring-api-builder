package com.keaz.tool.apibuilder.classgenerator.java.spring;

import com.keaz.tool.apibuilder.apiobject.*;
import com.keaz.tool.apibuilder.classgenerator.java.spring.DefinitionGenerator;
import com.keaz.tool.apibuilder.language.LanguageTypes;
import org.ainslec.picocog.PicoWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;

@RunWith(MockitoJUnitRunner.class)
public class DefinitionGeneratorTest {

    @Spy
    PicoWriter topWriter; // createDeferredWriter is final we cannot mock that
    @InjectMocks
    private DefinitionGenerator instance;
    private Resource resource;

    private Definition definition;

    private File resourcePackage;

    @Mock
    LanguageTypes languageTypes;

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

        definition = new Definition();
        HashMap<String, Property> properties = new HashMap<>();
        Property propOne = new Property();
        propOne.setType("integer");
        propOne.setFormat("int32");
        properties.put("propOne",propOne);

        Property propTwo = new Property();
        propTwo.setRef("#/definitions/DefTwo");
        properties.put("propTwo",propTwo);


        Property propThree = new Property();
        propThree.setType("array");
        Item item = new Item();
        item.setType("integer");
        item.setFormat("int32");
        propThree.setItems(item);
        properties.put("propThree",propThree);

        definition.setProperties(properties);
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

    @Test
    public void testGenerateSwaggerDef() {
        Mockito.when(languageTypes.getType("integer","int32")).thenReturn("Integer");
        instance.generate("DefinitionName", "definitions",definition, topWriter);

    }

}
