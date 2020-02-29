package com.keaz.tool.apibuilder.classgenerator;

import com.keaz.tool.apibuilder.apiobject.*;
import com.keaz.tool.apibuilder.language.LanguageTypes;
import org.ainslec.picocog.PicoWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ResourceGenerator extends AbstractClassGenerator<Resource> {

    public ResourceGenerator(LanguageTypes languageTypes) {
        super(languageTypes);
    }

    public void generate(File resourcePackage, Resource resource, PicoWriter topWriter){


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


    @Override
    public String generate( String definitionName,String packageName, Definition definition,PicoWriter topWriter) {


        createImports(topWriter,packageName);
        createClassDefinition(topWriter,definitionName);

        PicoWriter attributeWriter    = topWriter.createDeferredWriter();
        PicoWriter methodWriter = topWriter.createDeferredWriter();

        Map<String, Property> properties = definition.getProperties();
        Set<String> propertyNames = properties.keySet();
        for (String propertyName : propertyNames) {

            Property property = properties.get(propertyName);
            String ref = property.getRef();

            if(Objects.nonNull(ref)){
                String objectType = getObjectType(ref);
                addProperty(propertyName,objectType,attributeWriter,methodWriter);
                continue;
            }

            String type = property.getType();
            if(type.equals("array")){
                String languageType = getListType(property.getItems());
                String languageReturnType = createReturnList(languageType);

                addProperty(propertyName,languageReturnType,attributeWriter,methodWriter);
                continue;
            }

            String format = property.getFormat();
            String languageType = getType(type, format);
            addProperty(propertyName,languageType,attributeWriter,methodWriter);

        }

        closeClass(topWriter);
        return topWriter.toString();
    }


    private String createReturnList(String listType){
        return "List<"+listType+">";

    }


    private void addProperty(String propertyName,String propertyType,PicoWriter attributeWriter,PicoWriter methodWriter){
        createAttributes(attributeWriter,propertyName,propertyType);
        createSetter(methodWriter,propertyName,propertyType);

        createSetter(methodWriter,propertyName,propertyType);
        methodWriter.writeln("");
        createGetter(methodWriter,propertyName,propertyType);
    }



}
