package com.keaz.tool.apibuilder.apiobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Property {

    private static final String [] JAVA_PROPERTY_TYPES = {};

    private String type;
    private String format;
    private String example;
    private String description;
    @JsonProperty("enum")
    private List<String> enums;
    @JsonProperty("$ref")
    private String ref;
    private Map<String,String> xml;
    private Item items;


}
