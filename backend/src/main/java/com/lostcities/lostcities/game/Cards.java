package com.lostcities.lostcities.game;

import java.util.*;
import java.util.stream.IntStream;

public class Cards {

    private static final List<Card> deck = buildDeck();

    public static LinkedHashSet<Card> getShuffledDeck(Long seed) {
        List<Card> userDeck = new ArrayList<>(deck);
        Collections.shuffle(userDeck, new Random(seed));

        return new LinkedHashSet<>(userDeck);
    }

    private static List<Card> buildDeck() {
        List<Card> deck = new LinkedList<>();

        for(Color color : Color.values()) {
            deck.addAll(getCardsOfColor(color));
        }

        return deck;
    }

    private static Collection<Card> getCardsOfColor(Color color) {
        List<Card> cards = new ArrayList<>();

        cards.add(new Card(color, 1, 0));
        cards.add(new Card(color, 1, 1));
        cards.add(new Card(color, 1, 2));

        IntStream
                .range(2, 10)
                .mapToObj((i)-> new Card(color, i))
                .forEach(cards::add);

        return cards;
    }
}
