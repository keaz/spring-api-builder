package com.keaz.tool.apibuilder.classgenerator;

import com.keaz.tool.apibuilder.apiobject.*;
import org.ainslec.picocog.PicoWriter;

import java.io.File;
import java.util.Objects;
import java.util.Set;

public class ControllerGenerator extends AbstractClassGenerator<RootApi> {


    public void generate(File resourcePackage, RootApi rootApi, PicoWriter topWriter){

        String controller = rootApi.getController();

        createImports(topWriter,rootApi.getPackageName());
        createClassDefinition(topWriter,rootApi.getName(),controller);

        PicoWriter methodWriter = topWriter.createDeferredWriter();
        Set<Api> apis = rootApi.getApis();

        for (Api api : apis) {
            buildMethodAnnotations(methodWriter,api.getHttpMethod(),api.getUri());
            createMethod(methodWriter,api.getMethod(),api.getRequestBody(),api.getResponseBody(),api.getPathVariables(),api.getRequestParams());
        }

        closeClass(topWriter);

        writeToFile(controller,resourcePackage,topWriter.toString() );
    }


    private void createImports(PicoWriter topWriter, String packageName){
        topWriter.writeln("package " + packageName + ";");
        topWriter.writeln("");

        topWriter.writeln("import org.springframework.beans.factory.annotation.Autowired;");
        topWriter.writeln("import org.springframework.http.ResponseEntity;");
        topWriter.writeln("import org.springframework.web.bind.annotation.*;");

        topWriter.writeln("import java.util.List;");
        topWriter.writeln("");
    }

    private void createClassDefinition(PicoWriter topWriter, String name, String controller){
        topWriter.writeln("@RequestMapping(\""+name+"\")");
        topWriter.writeln("@RestController");
        topWriter.writeln_r("public class "+controller+" {");
        topWriter.writeln("");
    }

    private void createMethod(PicoWriter methodWriter, String method, String requestBody, String responseBody, Set<PathVariable> pathVariables, Set<RequestParam> requestParams){

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
