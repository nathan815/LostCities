package com.lostcities.lostcities.domain.game.card;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;


public class Card {

    public static final int WAGER_CARD_VALUE = 0;
    public static final int MIN_VALUE = 2;
    public static final int MAX_VALUE = 10;

    private int instance;

    private Color color;

    private int value;

    public Card(Color color, int value) {
        this.instance = 0;
        this.color = color;
        this.value = value;
    }

    public Card(Color color, int value, int instance) {
        this.instance = instance;
        this.color = color;
        this.value = value;
    }

    public static Card fromString(String cardString) {
        if(cardString == null) {
            return null;
        }
        String[] parts = cardString.split("_", 3);
        if(parts.length != 3) {
            throw new UnableToParseCardException(cardString);
        }

        try {
            return new Card(
                    Color.fromString(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2]));
        } catch (NumberFormatException exception) {
            throw new UnableToParseCardException(cardString, exception);
        }
    }

    public static Card wager(Color color, int instance) {
        return new Card(color, WAGER_CARD_VALUE, instance);
    }

    public static Card expedition(Color color, int number) {
        if(number < MIN_VALUE || number > MAX_VALUE) {
            throw new IllegalArgumentException("Expedition card number must be between 2 and 10");
        }
        return new Card(color, number, 0);
    }

    public Color getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public int getInstance() {
        return instance;
    }

    @JsonIgnore
    public boolean isWager() {
        return value == WAGER_CARD_VALUE;
    }

    @Override
    public String toString() {
        return color + "_" + value + "_" + instance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, value, instance);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(instance, card.instance) &&
                Objects.equals(color, card.color) &&
                Objects.equals(value, card.value);
    }
}
