package com.keaz.tool;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.keaz.tool.apibuilder.FileReader;
import com.keaz.tool.apibuilder.OutputGenerator;
import com.keaz.tool.apibuilder.apiobject.ApiDefinition;
import com.keaz.tool.apibuilder.classgenerator.spring.ControllerGenerator;
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

        ApiDefinition apiDefinition = new FileReader(new ObjectMapper(new YAMLFactory())).readFile(jsonFile);
        OutputGenerator outputGenerator = new OutputGenerator(new File(out), apiDefinition,new ResourceGenerator(), new ControllerGenerator());
        outputGenerator.generate();
    }


}
