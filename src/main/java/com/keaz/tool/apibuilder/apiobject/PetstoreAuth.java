package com.keaz.tool.apibuilder.apiobject;

import lombok.Data;

import java.util.Map;

@Data
public class PetstoreAuth {

    private String type;
    private String authorizationUrl;
    private String flow;
    private Map<String, String> scopes;

}
