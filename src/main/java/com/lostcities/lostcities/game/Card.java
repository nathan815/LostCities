package com.lostcities.lostcities.game;

import java.util.Objects;

//TODO ADD id and FIX hashcode
public class Card {
    Color color;
    Integer number;
    public Card(Color color, Integer number) {


        this.color = color;
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return color == card.color &&
                Objects.equals(number, card.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, number);
    }
}
