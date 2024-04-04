package com.stardevllc.starcore.utils.objecttester.codex;

import com.stardevllc.starcore.utils.objecttester.TypeCodex;

public class StringCodex extends TypeCodex {
    public StringCodex() {
        super("string", String.class, CharSequence.class);
    }

    @Override
    public String serialize(Object object) {
        if (isValidType(object)) {
            return (String) object;
        }
        return null;
    }

    @Override
    public Object deserialize(String serialized) {
        return serialized;
    }
}
