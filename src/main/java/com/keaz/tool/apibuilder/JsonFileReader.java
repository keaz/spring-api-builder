package com.keaz.tool.apibuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keaz.tool.apibuilder.apiobject.ApiDefinition;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class JsonFileReader {


    private final ObjectMapper  objectMapper;

    public JsonFileReader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ApiDefinition readJson(String jsonFile){
        if(Objects.isNull(jsonFile) || jsonFile.isBlank()){
            throw new IllegalArgumentException("Json file cannot be null or empty");
        }
        try {
            return objectMapper.readValue(new File(jsonFile), ApiDefinition.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
