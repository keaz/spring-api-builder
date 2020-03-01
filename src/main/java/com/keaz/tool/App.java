package com.keaz.tool;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.keaz.tool.apibuilder.FileReader;
import com.keaz.tool.apibuilder.OutputGenerator;
import com.keaz.tool.apibuilder.apiobject.ApiDefinition;
import com.keaz.tool.apibuilder.classgenerator.AbstractDefinitionGenerator;
import com.keaz.tool.apibuilder.language.LanguageTypes;
import com.keaz.tool.exception.ApiBuilderException;

import javax.annotation.CheckForNull;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        /**
         * --lang
         * --framework
         * --out
         * --api-yaml
         */

        String out = null;
        String jsonFile = null;
        String lang = null;
        String framework = null;

        for (String arg : args) {
            if (arg.startsWith("--out=")) {
                out = arg.replace("--out=", "");
                continue;
            }
            if (arg.startsWith("--api-yaml=")) {
                jsonFile = arg.replace("--api-yaml=", "");
                continue;
            }
            if (arg.startsWith("--lang=")) {
                lang = arg.replace("--lang=", "");
                continue;
            }
            if (arg.startsWith("--framework=")) {
                framework = arg.replace("--framework=", "");
            }
        }

        ApiDefinition apiDefinition = new FileReader(new ObjectMapper(new YAMLFactory())).readFile(jsonFile);


        LanguageTypes languageTypes = loadLanguage(lang);
        AbstractDefinitionGenerator definitionGenerator = loadDefinitionGenerator(lang, framework, languageTypes);
        AbstractDefinitionGenerator controllerGenerator = loadControllerGenerator(lang, framework, languageTypes);
        OutputGenerator outputGenerator = new OutputGenerator(new File(out), apiDefinition, definitionGenerator, controllerGenerator);
        outputGenerator.generate();
    }


    @CheckForNull
    private static LanguageTypes loadLanguage(String lang) {
        if(Objects.isNull(lang)){
            throw new ApiBuilderException("lang cannot be null");
        }
        String correctLang = lang.substring(0, 1).toUpperCase() + lang.substring(1);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            LanguageTypes languageTypes = (LanguageTypes) Class.forName("com.keaz.tool.apibuilder.language." + correctLang).getConstructors()[0].newInstance(objectMapper);
            languageTypes.init();
            return languageTypes;
        } catch (ClassNotFoundException e) {
            throw new ApiBuilderException("Language is not supported "+lang,e);
        }catch (InstantiationException | IllegalAccessException | InvocationTargetException e){
            throw new ApiBuilderException("Failed to create language class",e);
        }
    }

    private static AbstractDefinitionGenerator loadDefinitionGenerator(String lang, String framework, LanguageTypes languageTypes) {

        try {
            return  (AbstractDefinitionGenerator) Class.forName("com.keaz.tool.apibuilder.classgenerator." + lang + "." + framework + ".DefinitionGenerator")
                    .getConstructors()[0].newInstance(languageTypes);
        } catch (ClassNotFoundException e) {
            throw new ApiBuilderException(lang+" or "+ framework +" not supported ",e);
        }catch (InstantiationException | IllegalAccessException | InvocationTargetException e){
            throw new ApiBuilderException("Failed to create DefinitionGenerator class ",e);
        }
    }

    private static AbstractDefinitionGenerator loadControllerGenerator(String lang, String framework, LanguageTypes languageTypes) {

        try {
            return  (AbstractDefinitionGenerator) Class.forName("com.keaz.tool.apibuilder.classgenerator." + lang + "." + framework + ".ControllerGenerator")
                    .getConstructors()[0].newInstance(languageTypes);
        } catch (ClassNotFoundException e) {
            throw new ApiBuilderException(lang+" or "+ framework +" not supported ",e);
        }catch (InstantiationException | IllegalAccessException | InvocationTargetException e){
            throw new ApiBuilderException("Failed to create ControllerGenerator class ",e);
        }
    }


}
