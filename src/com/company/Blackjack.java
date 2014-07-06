package com.company;

import com.company.strategy.DealerStrategy;

import java.util.List;

/**
 * Created by Daria on 2014-07-05.
 */
public class Blackjack {
    public final Actor dealer;
    public final List<Actor> actors;
    public final int NUM_OF_DECKS = 1;
    public final int NUM_OF_BETTING_BOXES = 5;
    public static final String DEALERS_HAND = "Dealer's hand";
    private Hand[] bettingBoxes = new Hand[NUM_OF_BETTING_BOXES];
    private PackOfCards packOfCards;
    private boolean isDealerOpenHanded = false;


    public Blackjack(List<Actor> actors) {
        if (actors == null || actors.size() == 0) {
            throw new IllegalArgumentException("The \"eye in the sky\" didn't like what you are trying to do.");
        }
        this.dealer = new Actor("Dealer")
                .setStrategy(new DealerStrategy())
                .addHand(new Hand(DEALERS_HAND, 0));

        this.actors = actors;
    }

    public void play() {
        clean();
        placeBets();
        this.packOfCards.shuffle();
        dealInitialHandOf2();
        System.out.println(this);

        for (Hand hand : this.bettingBoxes) {
            if (hand == null) {
                continue;
            }

            while (hand.act(this) != Action.STAND) {
                hand.addCard(this.packOfCards.takeNext());
                System.out.println(this);
            }
        }

        this.isDealerOpenHanded = true;
        while (this.dealer.hands.get(DEALERS_HAND).act(this) != Action.STAND) {
            this.dealer.hands.get(DEALERS_HAND).addCard(this.packOfCards.takeNext());
        }

        score();
        System.out.println(this);
    }

    private void score() {
        int dealerScore = this.dealer.hands.get(DEALERS_HAND).getScore();
        if (dealerScore > 21) {
            dealerScore = 0;
        }

        for (Actor actor : actors) {
            for (Hand hand : actor.hands.values()) {
                int score = hand.getScore();
                if (score > 21) {
                    continue;
                } else if (score == 21) {
                    actor.incBudget(hand.bet * 3 / 2);
                } else if (score >= dealerScore) {
                    actor.incBudget(hand.bet);
                }
            }
        }
    }

    private void placeBets() {
        for (int i = 1; i <= NUM_OF_BETTING_BOXES; i++) {
            for (Actor actor : actors) {
                Integer j = actor.bet(i);
                if (j == null) {
                    continue;
                }
                if (j < 1 || j > 50) {
                    System.out.println("Min Bet: 1, Max Bet: 50");
                } else {
                    Hand hand = new Hand("box " + i, j);
                    bettingBoxes[i-1] = hand;
                    actor.addHand(hand);
                    actor.incBudget(-j);
                    break;
                }
            }
        }
    }

    private void dealInitialHandOf2() {
        for (int i = 0; i < 2; i++) {
            for (Hand hand : this.bettingBoxes) {
                if (hand == null) {
                    continue;
                }
                hand.addCard(this.packOfCards.takeNext());
            }
            this.dealer.hands.get(DEALERS_HAND).addCard(this.packOfCards.takeNext());
        }
    }

    private void clean() {
        this.isDealerOpenHanded = false;
        this.packOfCards = new PackOfCards();
        this.dealer.hands.get(DEALERS_HAND).clean();
        for (Actor player : this.actors) {
            player.clean();
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (this.isDealerOpenHanded) {
            result.append(dealer.hands.get(DEALERS_HAND));
        } else {
            result.append(String.format("Dealer's hand: %s, *", this.dealer.hands.get(DEALERS_HAND).getCards().get(0)));
            result.append("\n");
        }

        for (int i = 0; i < NUM_OF_BETTING_BOXES; i++) {
            Hand hand = this.bettingBoxes[i];
            result.append(String.format("Betting box %d: ", i+1));
            result.append(hand == null? "\n" : hand);
        }

        for (Actor actor : actors) {
            result.append(actor);
        }
        return result.toString();
    }
}
