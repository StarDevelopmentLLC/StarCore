package com.stardevllc.starcore.objecttester.codex;

import com.stardevllc.starcore.objecttester.TypeCodex;

public class ShortCodex extends TypeCodex {
    public ShortCodex() {
        super("short", Short.class, short.class);
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
        return Short.parseShort(serialized);
    }
}
