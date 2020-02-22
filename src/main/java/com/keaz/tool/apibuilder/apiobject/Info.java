package com.keaz.tool.apibuilder.apiobject;

import lombok.Data;

import java.util.Map;

@Data
public class Info {

    private String description;
    private String version;
    private String title;
    private String termsOfService;
    private Map<String,String> contact;
    private License license;

}
