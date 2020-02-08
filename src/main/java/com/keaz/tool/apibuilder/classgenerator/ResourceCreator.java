package com.keaz.tool.apibuilder.classgenerator;

import com.keaz.tool.apibuilder.apiobject.Attribute;
import com.keaz.tool.apibuilder.apiobject.Resource;
import org.ainslec.picocog.PicoWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

public class ResourceCreator {


    public void create(File resourcePackage, Resource resource,PicoWriter topWriter){

        File javaFile = new File(resourcePackage, resource.getName() + ".java");

        topWriter.writeln("package " + resource.getPackageName() + ";");
        topWriter.writeln("");
        topWriter.writeln_r ("public class "+resource.getName()+" {");
        topWriter.writeln("");

        PicoWriter attributeWriter    = topWriter.createDeferredWriter();
        PicoWriter methodWriter = topWriter.createDeferredWriter();
        Set<Attribute> attributes = resource.getAttributes();

        for (Attribute attribute : attributes) {

            String name = attribute.getName();
            String type = attribute.getType();
            attributeWriter.writeln("private "+ type +" "+name +";");

            methodWriter.writeln("");
            methodWriter.writeln_r("public void set" + name.substring(0, 1).toUpperCase() + name.substring(1) +"("+ type + " "+ name +"){");
            methodWriter.writeln("this."+name+" = " + name);
            methodWriter.writeln_l("}");

            methodWriter.writeln("");
            methodWriter.writeln_r("public "+ type +" get"+name.substring(0, 1).toUpperCase() + name.substring(1) +"(){");
            methodWriter.writeln("return this."+name+";");
            methodWriter.writeln_l("}");

        }

        topWriter.writeln("");
        topWriter.writeln_l("}");

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(javaFile);
            fileWriter.write(topWriter.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(!Objects.isNull(fileWriter)){
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
