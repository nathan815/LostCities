package com.lostcities.lostcities.game;

public enum Color {
    YELLOW,
    BLUE,
    WHITE,
    GREEN,
    RED;

    public static Color fromString(String string) {
        string = string.toLowerCase();
        for(Color color: values()) {
            if(color.toString().toLowerCase().equals(string)) {
                return color;
            }
        }

        return null;
    }
}
