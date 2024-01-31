package com.keaz.tool.apibuilder.classgenerator.spring;

import com.keaz.tool.apibuilder.apiobject.*;
import com.keaz.tool.apibuilder.classgenerator.AbstractClassGenerator;
import org.ainslec.picocog.PicoWriter;

import java.io.File;
import java.util.Objects;
import java.util.Set;

public class ControllerGenerator extends AbstractClassGenerator<RootApi> {

    private String [] returnColletion = {"LIST","SET"};

    enum ReturnValues {
        LIST("List"), SET("Set");

        private String name;

        ReturnValues(String name){
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public void generate(File resourcePackage, RootApi rootApi, PicoWriter top_Writer){

        String controller = rootApi.getController();

        createImports(top_Writer,rootApi.getPackageName());
        createClassDefinition(top_Writer,rootApi.getName(),controller);

        PicoWriter methodWriter = top_Writer.createDeferredWriter();
        Set<Api> apis = rootApi.getApis();

        for (Api api : apis) {
            buildMethodAnnotations(methodWriter,api.getHttpMethod(),api.getUri());
            createMethod(methodWriter,api.getMethod(),api.getRequestBody(),api.getResponseBody(),api.getPathVariables(),api.getRequestParams());
        }

        closeClass(top_Writer);

        writeToFile(controller,resourcePackage,top_Writer.toString() );
    }


    private void createImports(PicoWriter top_Writer, String packageName){
        top_Writer.writeln("package " + packageName + ";");
        top_Writer.writeln("");

        top_Writer.writeln("import org.springframework.beans.factory.annotation.Autowired;");
        top_Writer.writeln("import org.springframework.http.ResponseEntity;");
        top_Writer.writeln("import org.springframework.web.bind.annotation.*;");

        top_Writer.writeln("import java.util.*;");
        top_Writer.writeln("");
    }

    private void createClassDefinition(PicoWriter top_Writer, String name, String controller){
        top_Writer.writeln("@RequestMapping(\""+name+"\")");
        top_Writer.writeln("@RestController");
        top_Writer.writeln_r("public class "+controller+" {");
        top_Writer.writeln("");
    }

    private void createMethod(PicoWriter methodWriter, String method, String requestBody, String responseBody, Set<PathVariable> pathVariables, Set<RequestParam> requestParams){

        StringBuilder methodBuilder = new StringBuilder("public ");
        if(Objects.isNull(responseBody)){
            methodBuilder.append("void ").append(method);
        }else{
            if(responseBody.indexOf(':') > 0){
                String [] splits = responseBody.split(":");
                ReturnValues returnValue = ReturnValues.valueOf(splits[0]);
                methodBuilder.append(returnValue).append("<").append(splits[1]).append("> ").append(method);
            }else {
                methodBuilder.append(responseBody + " ").append(method);
            }
        }

        if(Objects.isNull(requestBody)){
            methodBuilder.append("(");
        }else{
            methodBuilder.append("("+requestBody +" "+requestBody.substring(0, 1).toLowerCase() + requestBody.substring(1) + " ");
        }

        createAdditionalParam(methodBuilder,pathVariables);
        createAdditionalParam(methodBuilder,requestParams);

        methodBuilder.append("){");
        methodWriter.writeln_r(methodBuilder.toString());
        methodWriter.writeln_l("}");
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
