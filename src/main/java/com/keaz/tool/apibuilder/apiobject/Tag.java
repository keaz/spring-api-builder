package com.keaz.tool.apibuilder.apiobject;

import lombok.Data;

import java.util.Map;

@Data
public class Tag {

    private String name;
    private String description;
    private Map<String,String> externalDocs;

}
