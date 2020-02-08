package com.keaz.tool.apibuilder.classgenerator;

import com.keaz.tool.apibuilder.apiobject.*;
import org.ainslec.picocog.PicoWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

public class ControllerCreator {


    public void create(File resourcePackage, RootApi rootApi, PicoWriter topWriter){

        String controller = rootApi.getController();
        File javaFile = new File(resourcePackage, controller + ".java");

        topWriter.writeln("package " + rootApi.getPackageName() + ";");
        topWriter.writeln("");

        createClassDefinition(topWriter,rootApi.getName(),controller);

        PicoWriter methodWriter = topWriter.createDeferredWriter();
        Set<Api> apis = rootApi.getApis();

        for (Api api : apis) {
            buildMethodAnnotations(methodWriter,api.getHttpMethod(),api.getUri());
            createMethodName(methodWriter,api.getMethod(),api.getRequestBody(),api.getResponseBody(),api.getPathVariables(),api.getRequestParams());
            createMethodBody(methodWriter);
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

    private void createMethodBody(PicoWriter methodWriter){
        methodWriter.writeln_l("}");
    }

    private void createClassDefinition(PicoWriter topWriter, String name, String controller){
        topWriter.writeln("@RequestMapping(\""+name+"\")");
        topWriter.writeln("@RestController");
        topWriter.writeln_r("public class "+controller+" {");
        topWriter.writeln("");
    }

    private void createMethodName(PicoWriter methodWriter, String method, String requestBody, String responseBody, Set<PathVariable> pathVariables, Set<RequestParam> requestParams){

        StringBuilder methodBuilder = new StringBuilder("public ");
        if(Objects.isNull(responseBody)){
            methodBuilder.append("void ").append(method);
        }else{
            methodBuilder.append(responseBody+ " ").append(method);
        }

        if(Objects.isNull(requestBody)){
            methodBuilder.append("(");
        }else{
            methodBuilder.append("("+requestBody +" "+requestBody + " ");
        }

        createAdditionalParam(methodBuilder,pathVariables);
        createAdditionalParam(methodBuilder,requestParams);

        methodBuilder.append("){");
        methodWriter.writeln_r(methodBuilder.toString());
    }


    private void createAdditionalParam(StringBuilder methodBuilder, Set<? extends Param> params){
        if(!Objects.isNull(params) && !params.isEmpty()){
            for (Param param : params) {
                methodBuilder.append(param);
            }
        }
    }

    private void buildMethodAnnotations(PicoWriter methodWriter, HttpMethod httpMethod, String uri ){
        methodWriter.writeln("");
        StringBuilder mappingBuilder = new StringBuilder();
        mappingBuilder.append(httpMethod);
        if(!Objects.isNull(uri) && !uri.isEmpty()){
            mappingBuilder.append("(\"").append(uri).append("\")");
        }

        methodWriter.writeln(mappingBuilder.toString());
    }

}
