package com.stardevllc.starcore.utils;

public final class ProgressBar {
    public static String of(int progress, int total, int barCount, String symbol, String completedColor, String notCompletedColor) {
        double completedPercentage = progress / (total * 1.0);
       return of(completedPercentage, barCount, symbol, completedColor, notCompletedColor);
    }
    
    public static String of(double completedPercentage, int barCount, String symbol, String completedColor, String notCompletedColor) {
        int completedBars = (int) (barCount * completedPercentage);
        int notCompletedBars = barCount - completedBars;

        return completedColor +
                symbol.repeat(Math.max(0, completedBars)) +
                notCompletedColor + symbol.repeat(Math.max(0, notCompletedBars));
    }
}