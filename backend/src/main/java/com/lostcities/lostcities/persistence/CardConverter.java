package com.lostcities.lostcities.persistence;

import com.lostcities.lostcities.domain.game.card.Card;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CardConverter implements AttributeConverter<Card, String> {
    @Override
    public String convertToDatabaseColumn(Card card) {
        return card.toString();
    }

    @Override
    public Card convertToEntityAttribute(String dbData) {
        return Card.fromString(dbData);
    }
}
