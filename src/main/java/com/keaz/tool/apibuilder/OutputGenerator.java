package com.keaz.tool.apibuilder;

import com.keaz.tool.apibuilder.apiobject.ApiDefinition;
import com.keaz.tool.apibuilder.apiobject.ApiObject;
import com.keaz.tool.apibuilder.apiobject.Resource;
import com.keaz.tool.apibuilder.apiobject.RootApi;
import com.keaz.tool.apibuilder.classgenerator.ClassGenerator;
import com.keaz.tool.apibuilder.classgenerator.ControllerGenerator;
import com.keaz.tool.apibuilder.classgenerator.ResourceGenerator;
import org.ainslec.picocog.PicoWriter;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class OutputGenerator {

    private final File outFolder;
    private final ApiDefinition apiDefinition;
    private final ClassGenerator resourceGenerator;
    private final ClassGenerator controllerGenerator;

    public OutputGenerator(File outFolder, ApiDefinition apiDefinition, ResourceGenerator resourceGenerator, ControllerGenerator controllerGenerator) {
        this.outFolder = outFolder;
        this.apiDefinition = apiDefinition;
        this.resourceGenerator = resourceGenerator;
        this.controllerGenerator = controllerGenerator;
    }

    public void generate(){
        if(Objects.isNull(outFolder)){
            throw new IllegalArgumentException("outFolder cannot be null");
        }

        if(Objects.isNull(apiDefinition)){
            throw new IllegalArgumentException("apiDefinition cannot be null");
        }

        if(outFolder.exists()){
            outFolder.delete();
        }
        outFolder.mkdirs();

        createClasses(resourceGenerator,apiDefinition.getResources());
        createClasses(controllerGenerator,apiDefinition.getRootApis());
    }


    private void createClasses(ClassGenerator classGenerator,Collection<? extends ApiObject> apiObjects){
        for (ApiObject apiObject : apiObjects) {
            File resourcePackage = createPackage(apiObject.getPackageName());
            classGenerator.generate(resourcePackage,apiObject,new PicoWriter());
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

}
