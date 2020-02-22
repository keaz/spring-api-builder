package com.keaz.tool.apibuilder.apiobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SecurityDefinition {


    private String petstoreAuth;
    private PetstoreAuth petstore_auth;
    @JsonProperty("api_key")
    private ApiKey apiKey;

}
