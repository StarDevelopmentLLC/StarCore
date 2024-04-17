package com.stardevllc.starcore.objecttester.codex;

import com.stardevllc.starcore.objecttester.TypeCodex;

public class DoubleCodex extends TypeCodex {
    public DoubleCodex() {
        super("double", Double.class, double.class);
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
        return Double.parseDouble(serialized);
    }
}
