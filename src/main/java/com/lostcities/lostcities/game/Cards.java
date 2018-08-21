package com.lostcities.lostcities.game;

import java.util.*;
import java.util.stream.IntStream;

public class Cards {

    static List<Card> deck;

    public static LinkedHashSet<Card> getDeck(Long seed) {
        if(deck == null) {
            deck = buildDeck();
        }

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
        Set<Card> cards = new HashSet<>();

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
