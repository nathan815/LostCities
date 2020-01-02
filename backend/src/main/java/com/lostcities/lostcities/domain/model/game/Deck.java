package com.lostcities.lostcities.domain.model.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

/**
 * A deck of Lost Cities cards for players to draw from
 */
public class Deck {

    private static final List<Card> STARTING_DECK = Collections.unmodifiableList(buildDeck());

    private List<Card> cards;

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    public Deck() {
        this(new ArrayList<>());
    }

    public static Deck getShuffledDeck(Random randomGenerator) {
        List<Card> cards = new ArrayList<>(STARTING_DECK);
        Collections.shuffle(cards, randomGenerator);
        return new Deck(cards);
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

        cards.add(Card.createWagerCard(color, 0));
        cards.add(Card.createWagerCard(color, 1));
        cards.add(Card.createWagerCard(color, 2));

        cards.addAll(IntStream.range(2, 10)
                .mapToObj((i) -> Card.createExpeditionCard(color, i))
                .collect(Collectors.toList()));

        return cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public boolean draw(Card card) {
        return cards.remove(card);
    }

    public Optional<Card> draw() {
        return cards.isEmpty() ? Optional.empty() : Optional.of(cards.remove(0));
    }

    public void add(Card card) {
        cards.add(card);
    }

    public int size() {
        return cards.size();
    }

    @Override
    public String toString() {
        return cards.stream().map(Card::toString).collect(joining(","));
    }
}
