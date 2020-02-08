package com.keaz.tool.apibuilder.apiobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = "type")
public class Attribute {

    private String name;
    private String type;

}
