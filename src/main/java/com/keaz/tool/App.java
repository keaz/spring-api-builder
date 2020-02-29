package com.keaz.tool;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.keaz.tool.apibuilder.FileReader;
import com.keaz.tool.apibuilder.OutputGenerator;
import com.keaz.tool.apibuilder.apiobject.ApiDefinition;
import com.keaz.tool.apibuilder.classgenerator.AbstractClassGenerator;
import com.keaz.tool.apibuilder.language.LanguageTypes;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

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
                continue;
            }
        }

        ApiDefinition apiDefinition = new FileReader(new ObjectMapper(new YAMLFactory())).readFile(jsonFile);


        LanguageTypes languageTypes = loadLanguage(lang);
        AbstractClassGenerator definitionGenerator = loadDefinitionGenerator(lang, framework, languageTypes);
        AbstractClassGenerator controllerGenerator = loadControllerGenerator(lang, framework, languageTypes);
        OutputGenerator outputGenerator = new OutputGenerator(new File(out), apiDefinition, definitionGenerator, controllerGenerator);
        outputGenerator.generate();
    }


    private static LanguageTypes loadLanguage(String lang) {
        String correctLang = lang.substring(0, 1).toUpperCase() + lang.substring(1);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            LanguageTypes languageTypes = (LanguageTypes) Class.forName("com.keaz.tool.apibuilder.language." + correctLang).getConstructors()[0].newInstance(objectMapper);
            languageTypes.init();
            return languageTypes;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static AbstractClassGenerator loadDefinitionGenerator(String lang, String framework, LanguageTypes languageTypes) {

        try {
            AbstractClassGenerator classGenerator = (AbstractClassGenerator) Class.forName("com.keaz.tool.apibuilder.classgenerator." + lang + "." + framework + ".DefinitionGenerator")
                    .getConstructors()[0].newInstance(languageTypes);
            return classGenerator;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static AbstractClassGenerator loadControllerGenerator(String lang, String framework, LanguageTypes languageTypes) {

        try {
            AbstractClassGenerator classGenerator = (AbstractClassGenerator) Class.forName("com.keaz.tool.apibuilder.classgenerator." + lang + "." + framework + ".ControllerGenerator")
                    .getConstructors()[0].newInstance(languageTypes);
            return classGenerator;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


}
