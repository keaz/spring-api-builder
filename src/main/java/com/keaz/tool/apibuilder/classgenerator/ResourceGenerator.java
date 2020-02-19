package com.keaz.tool.apibuilder.classgenerator;

import com.keaz.tool.apibuilder.apiobject.Attribute;
import com.keaz.tool.apibuilder.apiobject.Resource;
import org.ainslec.picocog.PicoWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

public class ResourceGenerator extends AbstractClassGenerator<Resource> {


    public void generate(File resourcePackage, Resource resource,PicoWriter topWriter){


        createImports(topWriter,resource.getPackageName());
        createClassDefinition(topWriter,resource.getName());

        PicoWriter attributeWriter    = topWriter.createDeferredWriter();
        PicoWriter methodWriter = topWriter.createDeferredWriter();
        Set<Attribute> attributes = resource.getAttributes();

        for (Attribute attribute : attributes) {

            String name = attribute.getName();
            String type = attribute.getType();

            createAttributes(attributeWriter,name,type);
            createSetter(methodWriter,name,type);

            methodWriter.writeln("");

            createGetter(methodWriter,name,type);
        }

        closeClass(topWriter);

        writeToFile(resource.getName(),resourcePackage,topWriter.toString());

    }

    private void createImports(PicoWriter topWriter, String packageName){
        topWriter.writeln("package " + packageName + ";");
        topWriter.writeln("");
    }

    private void createClassDefinition(PicoWriter topWriter, String className){
        topWriter.writeln_r("public class "+className+" {");
        topWriter.writeln("");
    }

    private void createSetter(PicoWriter methodWriter,String name, String type){
        methodWriter.writeln("");
        methodWriter.writeln_r("public void set" + name.substring(0, 1).toUpperCase() + name.substring(1) +"("+ type + " "+ name +"){");
        methodWriter.writeln("this."+name+" = " + name+";");
        methodWriter.writeln_l("}");
    }

    private void createGetter(PicoWriter methodWriter,String name, String type){
        methodWriter.writeln_r("public "+ type +" get"+name.substring(0, 1).toUpperCase() + name.substring(1) +"(){");
        methodWriter.writeln("return this."+name+";");
        methodWriter.writeln_l("}");
    }

    private void createAttributes(PicoWriter attributeWriter,String name, String type){
        attributeWriter.writeln("private "+ type +" "+name +";");
    }
}
