package com.keaz.tool.apibuilder;

import com.keaz.tool.apibuilder.apiobject.Api;
import com.keaz.tool.apibuilder.apiobject.ApiDefinition;
import com.keaz.tool.apibuilder.apiobject.Resource;
import com.keaz.tool.apibuilder.apiobject.RootApi;
import com.keaz.tool.apibuilder.classgenerator.ControllerCreator;
import com.keaz.tool.apibuilder.classgenerator.ResourceCreator;
import org.ainslec.picocog.PicoWriter;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class OutputGenerator {

    private final File outFolder;
    private final ApiDefinition apiDefinition;
    private final ResourceCreator resourceCreator;
    private final ControllerCreator controllerCreator;

    public OutputGenerator(File outFolder, ApiDefinition apiDefinition, ResourceCreator resourceCreator,ControllerCreator controllerCreator) {
        this.outFolder = outFolder;
        this.apiDefinition = apiDefinition;
        this.resourceCreator = resourceCreator;
        this.controllerCreator = controllerCreator;
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

        createResources(apiDefinition.getResources());
        createApis(apiDefinition.getRootApis());
    }

    private void createResources(List<Resource> resources){
        for (Resource resource : resources) {
            File resourcePackage = createPackage(resource.getPackageName());
            resourceCreator.create(resourcePackage,resource,new PicoWriter());
        }
    }

    private void createApis(Set<RootApi> apis){
        for (RootApi api : apis) {
            File resourcePackage = createPackage(api.getPackageName());
            controllerCreator.create(resourcePackage,api,new PicoWriter());
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
