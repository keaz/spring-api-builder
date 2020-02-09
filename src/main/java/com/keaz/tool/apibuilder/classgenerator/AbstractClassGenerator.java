package com.keaz.tool.apibuilder.classgenerator;

import com.keaz.tool.apibuilder.apiobject.ApiObject;
import org.ainslec.picocog.PicoWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public abstract class AbstractClassGenerator<T extends ApiObject> implements ClassGenerator<T>{


    protected void writeToFile(String controller, File resourcePackage, String classBody){
        File javaFile = new File(resourcePackage, controller + ".java");

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(javaFile);
            fileWriter.write(classBody);
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

    protected void closeClass(PicoWriter topWriter){
        topWriter.writeln("");
        topWriter.writeln_l("}");
    }

}
