package com.stardevllc.starcore.objecttester.codex;

import com.stardevllc.starcore.objecttester.TypeCodex;

public class CharCodex extends TypeCodex {
    public CharCodex() {
        super("char", Character.class, char.class);
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
        return serialized.charAt(0);
    }
}
