package com.lostcities.lostcities.game;

import java.util.*;
import java.util.stream.IntStream;

public class Cards {

    public static Set<Card> getDeck(Long seed) {
        List<Card> deck = new LinkedList<>();

        for(Color color : Color.values()) {
            deck.addAll(getCardsOfColor(color));
        }

        Collections.shuffle(deck, new Random(seed));

        return new LinkedHashSet<>(deck);
    }

    private static Collection<Card> getCardsOfColor(Color color) {
        Set<Card> cards = new HashSet<>();

        cards.add(new Card(color, 1));
        cards.add(new Card(color, 1));
        cards.add(new Card(color, 1));

        IntStream
                .range(2, 10)
                .mapToObj((i)-> new Card(color, i))
                .forEach(cards::add);

        return cards;
    }
}
