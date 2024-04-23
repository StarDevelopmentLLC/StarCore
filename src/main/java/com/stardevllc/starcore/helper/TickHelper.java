package com.stardevllc.starcore.helper;

import java.util.LinkedHashMap;
import java.util.Map;

public final class TickHelper {
    private TickHelper() {}

    public static final Map<String, Integer> nameToTicks = new LinkedHashMap<>();
    public static final int ticksPerDay = 24000;
    public static final int ticksPerHour = ticksPerDay / 24;
    public static final double ticksPerMinute = ticksPerHour / 60D;
    public static final double ticksPerSecond = ticksPerMinute / 60D;
    
    static {
        nameToTicks.put("sunrise", 23000);
        nameToTicks.put("dawn", 23000);

        nameToTicks.put("daystart", 0);
        nameToTicks.put("day", 0);

        nameToTicks.put("morning", 1000);

        nameToTicks.put("midday", 6000);
        nameToTicks.put("noon", 6000);

        nameToTicks.put("afternoon", 9000);

        nameToTicks.put("sunset", 12000);
        nameToTicks.put("dusk", 12000);
        nameToTicks.put("sundown", 12000);
        nameToTicks.put("nightfall", 12000);

        nameToTicks.put("nightstart", 14000);
        nameToTicks.put("night", 14000);

        nameToTicks.put("midnight", 18000);
    }
}