package com.lostcities.lostcities.domain.model.game;

public enum Color {
    YELLOW,
    BLUE,
    WHITE,
    GREEN,
    RED;

    public static Color fromString(String string) {
        String lcColor = string.toLowerCase();
        for(Color color : values()) {
            if(color.toString().toLowerCase().equals(lcColor)) {
                return color;
            }
        }

        throw new InvalidColorException(string);
    }
}
