package com.keaz.tool.apibuilder.classgenerator.java.spring;

import com.keaz.tool.apibuilder.apiobject.*;
import com.keaz.tool.apibuilder.classgenerator.AbstractDefinitionGenerator;
import com.keaz.tool.apibuilder.language.LanguageTypes;
import org.ainslec.picocog.PicoWriter;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerGenerator extends AbstractDefinitionGenerator<RootApi> {


    public ControllerGenerator(LanguageTypes languageTypes) {
        super(languageTypes);
    }

    public void generate(File resourcePackage, RootApi rootApi, PicoWriter topWriter) {

        String controller = rootApi.getController();

        createImports(topWriter, rootApi.getPackageName());
        createClassDefinition(topWriter, rootApi.getName(), controller);

        PicoWriter methodWriter = topWriter.createDeferredWriter();
        Set<Api> apis = rootApi.getApis();

        for (Api api : apis) {
            buildMethodAnnotations(methodWriter, api.getHttpMethod(), api.getUri());
            createMethod(methodWriter, api.getMethod(), api.getRequestBody(), api.getResponseBody(), api.getPathVariables(), api.getRequestParams());
        }

        closeClass(topWriter);

    }

    private void createImports(PicoWriter topWriter, String packageName) {
        topWriter.writeln("package " + packageName + ";");
        topWriter.writeln("");

        topWriter.writeln("import org.springframework.beans.factory.annotation.Autowired;");
        topWriter.writeln("import org.springframework.http.ResponseEntity;");
        topWriter.writeln("import org.springframework.web.bind.annotation.*;");

        topWriter.writeln("import java.util.*;");
        topWriter.writeln("");
    }

    private void createClassDefinition(PicoWriter topWriter, String name, String controller) {
        topWriter.writeln("@RequestMapping(\"" + name + "\")");
        topWriter.writeln("@RestController");
        topWriter.writeln_r("public class " + controller + " {");
        topWriter.writeln("");
    }

    private void createMethod(PicoWriter methodWriter, String method, String requestBody, String responseBody, Set<PathVariable> pathVariables, Set<RequestParam> requestParams) {

        StringBuilder methodBuilder = new StringBuilder("public ");
        if (Objects.isNull(responseBody)) {
            methodBuilder.append("void ").append(method);
        } else {
            if (responseBody.indexOf(':') != -1) {
                String[] splits = responseBody.split(":");
                ReturnValues returnValue = ReturnValues.valueOf(splits[0]);
                methodBuilder.append(returnValue).append("<").append(splits[1]).append("> ").append(method);
            } else {
                methodBuilder.append(responseBody + " ").append(method);
            }
        }

        if (Objects.isNull(requestBody)) {
            methodBuilder.append("(");
        } else {
            methodBuilder.append("(" + requestBody + " " + requestBody.substring(0, 1).toLowerCase() + requestBody.substring(1) + " ");
        }

        createAdditionalParam(methodBuilder, pathVariables);
        createAdditionalParam(methodBuilder, requestParams);

        methodBuilder.append("){");
        methodWriter.writeln_r(methodBuilder.toString());
        methodWriter.writeln_l("}");
    }

    private void createAdditionalParam(StringBuilder methodBuilder, Set<? extends Param> params) {
        if (!Objects.isNull(params) && !params.isEmpty()) {
            for (Param param : params) {
                methodBuilder.append(param);
            }
        }
    }

    private void buildMethodAnnotations(PicoWriter methodWriter, HttpMethod httpMethod, String uri) {
        methodWriter.writeln("");
        StringBuilder mappingBuilder = new StringBuilder();
        mappingBuilder.append(httpMethod);
        if (!Objects.isNull(uri) && !uri.isEmpty()) {
            mappingBuilder.append("(\"").append(uri).append("\")");
        }

        methodWriter.writeln(mappingBuilder.toString());
    }

    @Override
    public String generate(String definitionName, String packageName, Definition definition, PicoWriter topWriter) {
        return null;
    }


    public String generate(Map<String, Api> paths,PicoWriter topWriter){

        /**group all apis start with same root
            then group them by x-swagger-router-controller
                put all apis to grouped controller
         **/
        groupApisStartWithSameRoot(paths);

        return null;
    }

    /**
     * Group all the apis under same root api,
     * @param paths Map of api paths
     * @return API paths grouped under root apis
     */
    public Map<String, List<String>> groupApisStartWithSameRoot(Map<String, Api> paths){
        Set<String> rootPaths = paths.keySet();

        Pattern pattern = Pattern.compile("\\/(.*?)\\/");
        Map<String, List<String>> sameApiRoot = new HashMap<>();
        for (String rootPath : rootPaths) {
            Matcher matcher = pattern.matcher(rootPath);
            if(matcher.find()){
                String group = matcher.group(0);
                if(sameApiRoot.containsKey(group)){
                    sameApiRoot.get(group).add(rootPath);
                    continue;
                }

                List<String> apis = new LinkedList<>();
                apis.add(rootPath);
                sameApiRoot.put(group,apis);
            }
        }

        return sameApiRoot;
    }

    enum ReturnValues {
        LIST("List"), SET("Set");

        private String name;

        ReturnValues(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
