package com.keaz.tool.apibuilder;

import com.keaz.tool.apibuilder.apiobject.Api;
import com.keaz.tool.apibuilder.apiobject.ApiDefinition;
import com.keaz.tool.apibuilder.apiobject.ApiObject;
import com.keaz.tool.apibuilder.apiobject.Definition;
import com.keaz.tool.apibuilder.classgenerator.DefinitionGenerator;
import org.ainslec.picocog.PicoWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class OutputGenerator {

    private final File outFolder;
    private final ApiDefinition apiDefinition;
    private final DefinitionGenerator resourceGenerator;
    private final DefinitionGenerator controllerGenerator;

    public OutputGenerator(File outFolder, ApiDefinition apiDefinition, DefinitionGenerator definitionGenerator, DefinitionGenerator controllerGenerator) {
        this.outFolder = outFolder;
        this.apiDefinition = apiDefinition;
        this.resourceGenerator = definitionGenerator;
        this.controllerGenerator = controllerGenerator;
    }

    public void generate() {
        if (Objects.isNull(outFolder)) {
            throw new IllegalArgumentException("outFolder cannot be null");
        }

        if (Objects.isNull(apiDefinition)) {
            throw new IllegalArgumentException("apiDefinition cannot be null");
        }

        if (outFolder.exists()) {
            outFolder.delete();
        }
        outFolder.mkdirs();

        Map<String, Definition> definitions = apiDefinition.getDefinitions();
        createDefinitions(definitions);
        Map<String, Api> paths = apiDefinition.getPaths();
//        createClasses(resourceGenerator,apiDefinition.getResources());
//        createClasses(controllerGenerator,apiDefinition.getRootApis());
    }


    private void createDefinitions(Map<String, Definition> definitions) {
        Set<String> definitionNames = definitions.keySet();
        String packageName = "definitions";
        File resourcePackage = createPackage(packageName);
        for (String definitionName : definitionNames) {
            Definition definition = definitions.get(definitionName);
            String generatedClass = resourceGenerator.generate(definitionName, packageName, definition, new PicoWriter());
            writeToFile(definitionName, resourcePackage, generatedClass);
        }
    }

    private void createClasses(DefinitionGenerator definitionGenerator, Collection<? extends ApiObject> apiObjects) {
        for (ApiObject apiObject : apiObjects) {
            File resourcePackage = createPackage(apiObject.getPackageName());
            definitionGenerator.generate(resourcePackage, apiObject, new PicoWriter());
        }
    }


    private File createPackage(String packageName) {
        File resourcePackage = new File(outFolder, packageName.replace('.', File.separatorChar));
        if (resourcePackage.exists()) {
            resourcePackage.delete();
        }
        resourcePackage.mkdirs();
        return resourcePackage;
    }

    protected void writeToFile(String className, File resourcePackage, String classBody) {
        File javaFile = new File(resourcePackage, className + ".java");


        try (FileWriter fileWriter = new FileWriter(javaFile);) {
            fileWriter.write(classBody);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
