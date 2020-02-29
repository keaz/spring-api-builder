package com.keaz.tool.apibuilder.apiobject;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Method {

    private List<String> tags;
    private String summary;
    private String description;
    private String operationId;
    private List<String> consumes;
    private List<String> produces;
    private List<Parameter> parameters;
    private Map<String, Object> responses;
    private List<Object> security;
}
