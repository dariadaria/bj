package com.company;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Daria on 2014-07-05.
 */
public class Hand {
    public final String id;
    public final int bet;
    private Actor actor;
    private final List<Card> cards = new LinkedList<Card>();

    public Hand(String id, int bet) {
        this.id = id;
        this.bet = bet;
    }

    public Hand setActor(Actor actor) {
        this.actor = actor;
        return this;
    }

    public Action act(Blackjack blackjack) {
        return this.actor.act(this, blackjack);
    }

    public int getScore() {
        int count = 0;
        Queue<Card> aces = new LinkedList<Card>();
        for(Card card : this.cards) {
            count += card.count;
            if (card.count2 != null) {
                aces.add(card);
            }
        }

        while (count > 21 && !aces.isEmpty()) {
            Card ace = aces.poll();
            count -= ace.count;
            count += ace.count2;
        }

        return count;
    }

    public Hand addCard(Card card) {
        this.cards.add(card);
        return this;
    }

    public Iterable<Card> getCards() {
        return cards;
    }

    public Hand clean() {
        this.cards.clear();
        return  this;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("%s's cards: ", this.actor.name));
        for(Card card : this.getCards()) {
            result.append(String.format("%s, ", card));
        }
        result.append(String.format("total : %d", getScore()));
        result.append("\n");
        return result.toString();
    }

}
