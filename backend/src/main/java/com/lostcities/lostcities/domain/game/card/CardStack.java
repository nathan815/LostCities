package com.lostcities.lostcities.domain.game.card;

import java.util.*;
import java.util.function.Consumer;

import static java.util.stream.Collectors.joining;

public class CardStack implements Iterable<Card> {

    private final static int INITIAL_VALUE = -20;

    // Use a list for the cards so we can insert cards anywhere, as one might with a real stack of cards
    private List<Card> cards;

    public CardStack() {
        this(new LinkedList<>());
    }

    public CardStack(List<Card> cards) {
        this.cards = cards;
    }

    public void addToTop(Card card) {
        cards.add(card);
    }

    public void insertAt(int index, Card card) {
        cards.add(index, card);
    }

    public boolean remove(Card card) {
        return cards.remove(card);
    }

    public Optional<Card> removeTop() {
        return cards.isEmpty() ? Optional.empty() : Optional.of(cards.remove(cards.size() - 1));
    }

    public Optional<Card> getTop() {
        return cards.isEmpty() ? Optional.empty() : Optional.of(cards.get(cards.size() - 1));
    }

    public List<Card> getTopCards(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be positive");
        int count = Math.min(cards.size(), n);
        return cards.subList(cards.size() - count, cards.size());
    }

    public int calculateScore() {
        if (cards.isEmpty()) {
            return 0;
        }
        int value = INITIAL_VALUE, multiplier = 1;
        for(Card card : cards) {
            if(card.isWager()) multiplier++;
            else value += card.getValue();
        }
        return value * multiplier;
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int size() {
        return cards.size();
    }

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public Iterator<Card> iterator() {
        return cards.iterator();
    }

    @Override
    public void forEach(Consumer<? super Card> action) {
        cards.forEach(action);
    }

    @Override
    public Spliterator<Card> spliterator() {
        return cards.spliterator();
    }

    @Override
    public String toString() {
        return cards.stream().map(Card::toString).collect(joining(","));
    }
}
