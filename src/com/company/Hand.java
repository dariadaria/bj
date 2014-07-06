package com.company;

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
    private List<Card> hand = new LinkedList<Card>();

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
        for(Card card : this.getCards()) {
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
        this.hand.add(card);
        return this;
    }

    public List<Card> getCards() {
        return hand;
    }

    public Hand clean() {
        this.hand = new LinkedList<Card>();
        return  this;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("%s's hand: ", this.actor.name));
        for(Card card : this.getCards()) {
            result.append(String.format("%s, ", card));
        }
        result.append(String.format("total : %d", getScore()));
        result.append("\n");
        return result.toString();
    }
}
