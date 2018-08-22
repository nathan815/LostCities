package com.lostcities.lostcities.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lostcities.lostcities.game.exceptions.UnableToParseCardException;

import java.util.Objects;


public class Card {
    @JsonIgnore
    private Integer instance;
    @JsonProperty
    private Color color;
    @JsonProperty
    private Integer number;

    public static Card fromString(String cardString) {
        String[] parts = cardString.split("_", 3);
        try {
            if (parts.length == 3) {

                return new Card(
                        Color.fromString(parts[0]),
                        Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]));
            }

            //TODO make exception for improper card
            throw new UnableToParseCardException("Invalid card format");
        } catch (RuntimeException exception) {
            throw new UnableToParseCardException(cardString, exception);
        }
    }

    public Card(Color color, Integer number) {
        this.instance = 0;
        this.color = color;
        this.number = number;
    }

    public Card(Color color, Integer number, Integer instance) {
        this.instance = instance;
        this.color = color;
        this.number = number;
    }

    public Color getColor() {
        return color;
    }

    public Integer getNumber() {
        return number;
    }

    public boolean isMultiplier() {
        return this.number.equals(1);
    }

    @JsonProperty
    @Override
    public String toString() {
        return color + "_" + number + "_" +instance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, number, instance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(instance, card.instance) &&
                color == card.color &&
                Objects.equals(number, card.number);
    }
}
