package com.company;

import com.company.strategy.IStrategy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daria on 2014-07-06.
 */
public class Actor {
    private IStrategy strategy;
    public final String name;
    public Map<String, Hand> hands = new HashMap<String, Hand>();
    private int budget;

    public Actor(String name) {
        this.name = name;
    }

    public Actor setStrategy(IStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public Actor setBudget(int budget) {
        this.budget = budget;
        return this;
    }

    public Actor incBudget(int inc) {
        this.budget += inc;
        return this;
    }

    public Action act(Hand hand, Blackjack blackjack) {
        if (this.strategy == null) {
            throw new IllegalStateException("Strategy should be selected first");
        }

        return this.strategy.play(hand, blackjack);
    }

    public Actor addHand(Hand hand) {
        hand.setActor(this);
        this.hands.put(hand.id, hand);
        return this;
    }

    public Integer bet(int box) {
        Integer b = this.strategy.bet(box);
        if (b!= null && b > this.budget) {
            System.out.println("You can't afford it any more");
            b = null;
        }
        return b;
    }

    public void clean() {
        this.hands = new HashMap<String, Hand>();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.name);
        result.append(String.format(" >> current budget : %d", this.budget));
        result.append("\n");
        return result.toString();
    }
}
