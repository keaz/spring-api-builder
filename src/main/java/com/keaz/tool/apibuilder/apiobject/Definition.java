package com.keaz.tool.apibuilder.apiobject;

import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class Definition {


    private Map<String,Property> properties;
    private Map<String,Object> xml;
    private Set<String> required;

}
