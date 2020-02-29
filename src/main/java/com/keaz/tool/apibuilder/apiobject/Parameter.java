package com.keaz.tool.apibuilder.apiobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Parameter {


    private String in;
    private String name;
    private String description;
    private Boolean required;
    private String type;
    private String format;
    private ResponseBody schema;
    private Map<String, Object> items;
    private String collectionFormat;
    @JsonProperty("default")
    private String defaults;
    @JsonProperty("enum")
    private List<String> enums;

}
