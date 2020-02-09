package com.keaz.tool;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keaz.tool.apibuilder.JsonFileReader;
import com.keaz.tool.apibuilder.OutputGenerator;
import com.keaz.tool.apibuilder.apiobject.ApiDefinition;
import com.keaz.tool.apibuilder.classgenerator.ControllerGenerator;
import com.keaz.tool.apibuilder.classgenerator.ResourceGenerator;

import java.io.File;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        /**
         * --out
         * --api-json
         */

        String out = null;
        String jsonFile = null;
        for (String arg : args) {
            if(arg.startsWith("--out=")){
                out = arg.replace("--out=","");
                continue;
            }
            if(arg.startsWith("--api-json=")){
                jsonFile = arg.replace("--api-json=","");
                continue;
            }
        }

        ApiDefinition apiDefinition = new JsonFileReader(new ObjectMapper()).readJson(jsonFile);
        OutputGenerator outputGenerator = new OutputGenerator(new File(out), apiDefinition,new ResourceGenerator(), new ControllerGenerator());
        outputGenerator.generate();
    }


}
