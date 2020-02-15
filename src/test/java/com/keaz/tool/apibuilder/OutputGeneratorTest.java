package com.keaz.tool.apibuilder;

import com.keaz.tool.apibuilder.apiobject.ApiDefinition;
import com.keaz.tool.apibuilder.apiobject.ApiObject;
import com.keaz.tool.apibuilder.apiobject.Resource;
import com.keaz.tool.apibuilder.apiobject.RootApi;
import com.keaz.tool.apibuilder.classgenerator.ResourceGenerator;
import com.keaz.tool.apibuilder.classgenerator.spring.ControllerGenerator;
import org.ainslec.picocog.PicoWriter;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;

@RunWith(MockitoJUnitRunner.class)
public class OutputGeneratorTest  {


    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private File outFolder;

    @Mock
    private ApiDefinition apiDefinition;

    @Mock
    private ResourceGenerator resourceGenerator;

    @Mock
    private ControllerGenerator controllerGenerator;

    @Mock
    private ApiObject apiObject;

    @Mock
    private PicoWriter picoWriter;

    @InjectMocks
    private OutputGenerator outputGenerator;


    @Before
    public void beforeTests() throws NoSuchFieldException, IllegalAccessException{
        outFolder = new File("test");
        outFolder.deleteOnExit();

        apiDefinition =  new ApiDefinition();
        LinkedList<Resource> resources = new LinkedList<>();
        Resource resource = new Resource();
        resource.setPackageName("test");
        resources.add(resource);
        apiDefinition.setResources(resources);

        RootApi rootApi = new RootApi();
        rootApi.setPackageName("test");
        apiDefinition.setRootApis(Collections.singleton(rootApi));


        Field apiDefinitionField = OutputGenerator.class.getDeclaredField("apiDefinition");
        apiDefinitionField.setAccessible(true);
        apiDefinitionField.set(outputGenerator,apiDefinition);


        outFolder = new File("test");
        outFolder.mkdir();

        Field outFolderFiled = OutputGenerator.class.getDeclaredField("outFolder");
        outFolderFiled.setAccessible(true);
        outFolderFiled.set(outputGenerator,outFolder);
    }


    @After
    public void afterTest(){
        new File(outFolder,"test").delete();
        outFolder.delete();
    }


    @Test
    public void testGenerate(){
        outFolder.mkdir();
        outputGenerator.generate();
        Assert.assertTrue(new File(outFolder,"test").exists());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGenerateOutFoldrNull() throws NoSuchFieldException, IllegalAccessException{

//        doNothing().when(classGenerator).generate(resourcePackage,apiObject,picoWriter);

        Field outFolder = OutputGenerator.class.getDeclaredField("outFolder");
        outFolder.setAccessible(true);
        outFolder.set(outputGenerator,null);

        outputGenerator.generate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGenerateApiDefinitionNull() throws NoSuchFieldException, IllegalAccessException{

        Field outFolder = OutputGenerator.class.getDeclaredField("apiDefinition");
        outFolder.setAccessible(true);
        outFolder.set(outputGenerator,null);

        outputGenerator.generate();
    }


}
