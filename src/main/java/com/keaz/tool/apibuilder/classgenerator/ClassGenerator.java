package com.keaz.tool.apibuilder.classgenerator;

import com.keaz.tool.apibuilder.apiobject.ApiObject;
import org.ainslec.picocog.PicoWriter;

import java.io.File;

public interface ClassGenerator <T extends ApiObject> {

    void generate(File resourcePackage, T resource, PicoWriter topWriter);
}
