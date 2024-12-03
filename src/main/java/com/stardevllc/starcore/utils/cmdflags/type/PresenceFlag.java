package com.stardevllc.starcore.utils.cmdflags.type;

import com.stardevllc.starcore.utils.cmdflags.FlagType;

public class PresenceFlag extends AbstractFlag {
    public PresenceFlag(String id, String name) {
        super(id, name);
    }

    @Override
    public Object defaultValue() {
        return false;
    }

    @Override
    public FlagType type() {
        return FlagType.PRESENCE;
    }
}
