package com.lostcities.lostcities.domain.game;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Converter for Game.Status enum
 */
@Converter(autoApply = true)
public class GameStatusEnumConverter implements AttributeConverter<Game.Status, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Game.Status status) {
        return status.getCode();
    }

    @Override
    public Game.Status convertToEntityAttribute(Integer stateCode) {
        return Game.Status.fromCode(stateCode);
    }
}
