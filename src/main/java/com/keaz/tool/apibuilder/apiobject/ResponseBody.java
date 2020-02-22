package com.keaz.tool.apibuilder.apiobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class ResponseBody {

    private String type;
    private String format;
    private Integer minimum;
    @JsonProperty("$ref")
    private String ref;
    private Map<String,Map<String,Object>> properties;
    private Map<String,String> items;

}
