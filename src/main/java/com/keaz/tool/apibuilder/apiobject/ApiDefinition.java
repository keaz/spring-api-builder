package com.keaz.tool.apibuilder.apiobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class ApiDefinition {

    private List<Resource> resources;

    @JsonIgnore
    private Set<RootApi> rootApis;

    private String swagger;
    private Info info;
    private Map<String,Api> paths;
    private String basePath;
    private Set<Tag> tags;
    private Set<Scheme> schemes;
    private SecurityDefinition securityDefinitions;
    private Map<String,Object> definitions;
    private ExternalDocs externalDocs;

}
