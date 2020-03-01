package com.keaz.tool.apibuilder.classgenerator.java.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.keaz.tool.apibuilder.FileReader;
import com.keaz.tool.apibuilder.apiobject.*;
import org.ainslec.picocog.PicoWriter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class ControllerGeneratorTest {


    @Spy
    PicoWriter topWriter; // createDeferredWriter is final we cannot mock that
    private RootApi rootApi;
    private File resourcePackage;

    @InjectMocks
    private ControllerGenerator controllerGenerator;

    @Before
    public void beforeTests() {

        resourcePackage = new File("test");
        resourcePackage.mkdirs();

        rootApi = new RootApi();
        rootApi.setPackageName("test");
        rootApi.setName("test");
        rootApi.setController("Test");
        Api api = new Api();
        api.setHttpMethod(HttpMethod.GET);
        api.setMethod("test");
        api.setRequestBody("Test");
        api.setUri("test");
        PathVariable pathVariable = new PathVariable();
        pathVariable.setName("test");
        pathVariable.setType("String");
        api.setPathVariables(Collections.singleton(pathVariable));

        rootApi.setApis(Collections.singleton(api));
    }

    @After
    public void afterTest() {
        new File(resourcePackage, "Test.java").delete();
        resourcePackage.delete();
    }


    @Test
    public void testGenerateWithoutResponseBody() {
        controllerGenerator.generate(resourcePackage, rootApi, topWriter);
    }

    @Test
    public void testGenerateWithResponseBody() {
        rootApi.getApis().iterator().next().setResponseBody("Test");
        controllerGenerator.generate(resourcePackage, rootApi, topWriter);
    }

    @Test
    public void testGenerateWithListResponseBody() {
        rootApi.getApis().iterator().next().setResponseBody("LIST:Test");
        controllerGenerator.generate(resourcePackage, rootApi, topWriter);
    }


    @Test
    public void testGenerateWithoutRequestBody() {
        rootApi.getApis().iterator().next().setRequestBody(null);
        controllerGenerator.generate(resourcePackage, rootApi, topWriter);
    }


    //groupApisStartWithSameRoot

    @Test
    public void testGroupApisStartWithSameRoot() {

        ApiDefinition apiDefinition = new FileReader(new ObjectMapper(new YAMLFactory())).readFile("sample-api.yaml");
        apiDefinition.getPaths();
        Map<String, List<String>> stringListMap = controllerGenerator.groupApisStartWithSameRoot(apiDefinition.getPaths());
        Assert.assertEquals(3,stringListMap.size());
    }
}
