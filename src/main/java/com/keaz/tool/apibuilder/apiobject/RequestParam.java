package com.keaz.tool.apibuilder.apiobject;

import lombok.Data;

@Data
public class RequestParam extends Param{

    private String name;
    private String type;

    @Override
    public String toString() {
        return "@RequestParam(\""+name+"\")"+" "+type+" "+name+" ";
    }
}
