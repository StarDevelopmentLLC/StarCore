package com.stardevllc.starcore.objecttester.codex;

import com.stardevllc.starcore.objecttester.TypeCodex;

public class BooleanCodex extends TypeCodex {
    public BooleanCodex() {
        super("boolean", Boolean.class, boolean.class);
    }

    @Override
    public String serialize(Object object) {
        if (isValidType(object)) {
            return object + "";
        }
        return null;
    }

    @Override
    public Object deserialize(String serialized) {
        return Boolean.parseBoolean(serialized);
    }
}
