package com.keaz.tool.apibuilder.apiobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ApiDefinition {

    private List<Resource> resources;

    @JsonProperty("root_apis")
    private Set<RootApi> rootApis;

}
