package com.keaz.tool.apibuilder.apiobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"attributes"})
public class Resource extends ApiObject{

    @JsonProperty("package")
    private String packageName;
    private String name;

    private Set<Attribute> attributes;
}
