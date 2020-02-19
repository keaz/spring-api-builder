package com.keaz.tool.apibuilder.apiobject;

import lombok.Data;

@Data
public class PathVariable extends Param {

    private String name;
    private String type;

    @Override
    public String toString() {
        return "@PathVariable(\""+name+"\")"+" "+type+" "+name+" ";
    }
}
