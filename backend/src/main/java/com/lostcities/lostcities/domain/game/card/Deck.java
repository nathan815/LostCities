package com.lostcities.lostcities.domain.game.card;

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
 * A deck of cards for players to draw from backed by a CardStack
 */
public class Deck {

    private static final List<Card> STARTING_DECK = Collections.unmodifiableList(buildStartingCardList());

    private CardStack cards;

    public Deck(CardStack cards) {
        this.cards = cards;
    }

    public Deck() {
        this(new CardStack());
    }

    public static Deck fromList(List<Card> cards) {
        return new Deck(new CardStack(cards));
    }

    public static Deck getShuffledDeck(Random randomGenerator) {
        List<Card> cards = new ArrayList<>(STARTING_DECK);
        Collections.shuffle(cards, randomGenerator);
        return fromList(cards);
    }

    private static List<Card> buildStartingCardList() {
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

        cards.addAll(IntStream.range(2, 11)
                .mapToObj((i) -> Card.createExpeditionCard(color, i))
                .collect(Collectors.toList()));

        return cards;
    }

    public CardStack getCards() {
        return cards;
    }

    public boolean draw(Card card) {
        return cards.remove(card);
    }

    public Optional<Card> draw() {
        return cards.removeTop();
    }

    public Optional<Card> getTop() {
        return cards.getTop();
    }

    public int size() {
        return cards.size();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}
