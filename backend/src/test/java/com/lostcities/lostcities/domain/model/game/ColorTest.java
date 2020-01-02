package com.lostcities.lostcities.domain.model.game;

import org.junit.Test;

import static org.junit.Assert.*;

public class ColorTest {
    @Test
    public void fromString_allCaps_shouldReturnCorrectInstance() {
        assertEquals(Color.BLUE, Color.fromString("BLUE"));
        assertEquals(Color.RED, Color.fromString("RED"));
        assertEquals(Color.GREEN, Color.fromString("GREEN"));
        assertEquals(Color.YELLOW, Color.fromString("YELLOW"));
        assertEquals(Color.WHITE, Color.fromString("WHITE"));
    }

    @Test
    public void fromString_varyingCaps_shouldReturnCorrectInstance() {
        assertEquals(Color.BLUE, Color.fromString("Blue"));
        assertEquals(Color.RED, Color.fromString("rEd"));
        assertEquals(Color.GREEN, Color.fromString("green"));
    }

    @Test(expected = InvalidColorException.class)
    public void fromString_invalidName_shouldThrowException() {
        Color.fromString("hello");
    }
}
