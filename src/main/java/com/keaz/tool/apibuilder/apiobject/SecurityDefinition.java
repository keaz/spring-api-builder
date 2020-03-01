package com.keaz.tool.apibuilder.apiobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SecurityDefinition {

    @JsonProperty("petstore_auth")
    private PetstoreAuth petstoreAuth;
    @JsonProperty("api_key")
    private ApiKey apiKey;

}
