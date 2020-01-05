package com.lostcities.lostcities.domain.game.card;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class CardTestUtils {

    public static Set<Card> cardSetFromStrings(String... cards) {
        return Lists.newArrayList(cards).stream().map(Card::fromString).collect(toSet());
    }

    public static List<Card> cardListFromStrings(String... cards) {
        return Lists.newArrayList(cards).stream().map(Card::fromString).collect(toList());
    }

    public static CardStack cardStackFromCardStrings(String... cards) {
        return new CardStack(cardListFromStrings(cards));
    }
}
