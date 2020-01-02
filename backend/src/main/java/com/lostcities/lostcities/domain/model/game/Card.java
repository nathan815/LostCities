package com.lostcities.lostcities.domain.model.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;


public class Card {

    private static final int WAGER_CARD_NUMBER = 1;

    @JsonIgnore
    private int instance;

    @JsonProperty
    private Color color;

    @JsonProperty
    private int number;

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

    public static Card fromString(String cardString) {
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

    public static Card createWagerCard(Color color, int instance) {
        return new Card(color, WAGER_CARD_NUMBER, instance);
    }

    public static Card createExpeditionCard(Color color, int number) {
        if(number < 2 || number > 10) {
            throw new IllegalArgumentException("Expedition card number must be between 2 and 10");
        }
        return new Card(color, number, 0);
    }

    public Color getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    @JsonProperty
    public boolean isWager() {
        return number == WAGER_CARD_NUMBER;
    }

    @JsonProperty
    @Override
    public String toString() {
        return color + "_" + number + "_" + instance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, number, instance);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(instance, card.instance) &&
                Objects.equals(color, card.color) &&
                Objects.equals(number, card.number);
    }
}
