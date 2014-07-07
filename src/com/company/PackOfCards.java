package com.company;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Daria on 2014-07-05.
 */
public class PackOfCards {
    private static final int DECK_SIZE = 52;
    public static final Card[] DECK = getDeck52();
    private final Queue<Card> cards  = new LinkedList<Card>();

    public PackOfCards() {
        this(1);
    }

    public PackOfCards(int numberOfDecks) {
        if (numberOfDecks < 0 || numberOfDecks > 8) {
            throw new IllegalArgumentException("Number of decks value does not fall within the expected range [1,8].");
        }

        for (int i = 0; i < numberOfDecks; i++) {
            for(Card card : DECK) { //Every card in a deck gets added to the list as every card has 4 suits
                this.cards.add(card);
                this.cards.add(card);
                this.cards.add(card);
                this.cards.add(card);
            }
        }
    }

    public PackOfCards shuffle() {
        Collections.shuffle((List)this.cards);
        return this;
    }

    public Card takeNext() {
        if (this.cards.size() == 0) {
            return null;
        }

        return this.cards.poll();
    }

    private static Card[] getDeck52() {
        Card[] cards = new Card[13];
        cards[0] = new Card("2", 2);
        cards[1] = new Card("3", 3);
        cards[2] = new Card("4", 4);
        cards[3] = new Card("5", 5);
        cards[4] = new Card("6", 6);
        cards[5] = new Card("7", 7);
        cards[6] = new Card("8", 8);
        cards[7] = new Card("9", 9);
        cards[8] = new Card("10", 10);
        cards[9] = new Card("King", 10);
        cards[10] = new Card("Queen", 10);
        cards[11] = new Card("Jack", 10);
        cards[12] = new Card("Ace", 11, 1);

        return cards;
    }
}
