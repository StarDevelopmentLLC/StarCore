package com.stardevllc.starcore.utils.cmdflags;

import java.util.Map;

public record ParseResult(String[] args, Map<Flag, Object> flagValues) {
}
