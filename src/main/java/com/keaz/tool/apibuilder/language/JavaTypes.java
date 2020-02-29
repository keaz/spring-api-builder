package com.keaz.tool.apibuilder.language;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class JavaTypes implements LanguageTypes {

    private static final String JAVA_TYPE_FILE = "java-types.json";
    private final ObjectMapper objectMapper;
    private JsonNode jsonNode;

    public JavaTypes(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void init() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream resource = classloader.getResourceAsStream(JAVA_TYPE_FILE);
        try {
            jsonNode = objectMapper.readTree(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getType(String type, String format) {
        if(Objects.isNull(format)){
            return jsonNode.path(type).path("").asText();
        }
        return jsonNode.path(type).path(format).asText();
    }



}
