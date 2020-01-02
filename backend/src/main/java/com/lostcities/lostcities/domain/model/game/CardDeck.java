package com.lostcities.lostcities.domain.model.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

public class CardDeck {

    private static final List<Card> STARTING_DECK = Collections.unmodifiableList(buildDeck());

    private List<Card> cards;

    public CardDeck(List<Card> cards) {
        this.cards = cards;
    }

    public CardDeck() {
        this(new ArrayList<>());
    }

    public static CardDeck getShuffledDeck(Random randomGenerator) {
        List<Card> cards = new ArrayList<>(STARTING_DECK);
        Collections.shuffle(cards, randomGenerator);
        return new CardDeck(cards);
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

        cards.addAll(IntStream.range(2, 10)
                .mapToObj((i) -> new Card(color, i))
                .collect(Collectors.toList()));

        return cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public boolean draw(Card card) {
        return cards.remove(card);
    }

    public Card draw() {
        if(cards.isEmpty()) {
            throw new RuntimeException("Deck is empty");
        }
        return cards.remove(0);
    }

    public void add(Card card) {
        cards.add(card);
    }

    public int size() {
        return cards.size();
    }

    public Optional<Card> getFirst() {
        return cards.stream().findFirst();
    }

    @Override
    public String toString() {
        return cards.stream().map(Card::toString).collect(joining(","));
    }
}
