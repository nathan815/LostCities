package com.lostcities.lostcities.domain.game.card;

import com.google.common.collect.Lists;
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
    public static final int STARTING_SIZE = STARTING_DECK.size();

    private CardStack cards;

    public Deck(CardStack cards) {
        this.cards = cards;
    }

    public Deck() {
        this(new CardStack());
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

        cards.add(Card.wager(color, 0));
        cards.add(Card.wager(color, 1));
        cards.add(Card.wager(color, 2));

        cards.addAll(IntStream.rangeClosed(Card.MIN_VALUE, Card.MAX_VALUE)
                .mapToObj((i) -> Card.expedition(color, i))
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

    public static Deck createShuffled(Random randomGenerator) {
        List<Card> cards = new ArrayList<>(STARTING_DECK);
        Collections.shuffle(cards, randomGenerator);
        return fromList(cards);
    }

    public static Deck create() {
        return fromList(new ArrayList<>(STARTING_DECK));
    }

    public static Deck of(Card ...cards) {
        return Deck.fromList(Lists.newArrayList(cards));
    }

    public static Deck fromList(List<Card> cards) {
        return new Deck(new CardStack(cards));
    }
}
