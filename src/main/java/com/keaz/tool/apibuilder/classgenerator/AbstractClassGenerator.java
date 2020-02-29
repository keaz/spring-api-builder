package com.keaz.tool.apibuilder.classgenerator;

import com.keaz.tool.apibuilder.apiobject.ApiObject;
import com.keaz.tool.apibuilder.apiobject.Item;
import com.keaz.tool.apibuilder.language.LanguageTypes;
import org.ainslec.picocog.PicoWriter;

import java.util.Objects;

public abstract class AbstractClassGenerator<T extends ApiObject> implements ClassGenerator<T> {

    protected final LanguageTypes languageTypes;

    public AbstractClassGenerator(LanguageTypes languageTypes) {
        this.languageTypes = languageTypes;
    }


    protected void closeClass(PicoWriter topWriter) {
        topWriter.writeln("");
        topWriter.writeln_l("}");
    }


    protected String getListType(Item items) {
        String ref = items.getRef();

        if (Objects.nonNull(ref)) {
            return getObjectType(ref);
        }

        return getType(items.getType(), items.getFormat());

    }

    protected String getType(String type, String format) {
        return languageTypes.getType(type, format);
    }

    protected String getObjectType(String ref) {
        String[] split = ref.split("/");
        if (Objects.isNull(ref)) {
            throw new RuntimeException();
        }
        return split[split.length - 1];
    }

}
