package com.keaz.tool.apibuilder.apiobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "apis")
public class RootApi implements ApiObject {

    private String name;
    @JsonProperty("package")
    private String packageName;
    private String controller;

    private Set<Api> apis;

}
