package com.keaz.tool.apibuilder.apiobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Api {

    @EqualsAndHashCode.Include
    @JsonProperty("http_method")
    private HttpMethod httpMethod;
    @EqualsAndHashCode.Include
    private String uri;
    @JsonProperty("request_body")
    private String requestBody;
    private String method;
    @JsonProperty("response_body")
    private String responseBody;
    @JsonProperty("path_variables")
    private Set<PathVariable> pathVariables;
    @JsonProperty("request_params")
    private Set<RequestParam> requestParams;


    private Method post;
    private Method put;
    private Method patch;
    private Method get;
    private Method delete;


}
