package com.company.strategy;

import com.company.Action;
import com.company.Card;
import com.company.PackOfCards;
import com.company.Actor;
import com.company.Hand;
import com.company.Blackjack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daria on 2014-07-05.
 */
public class CountingStrategy implements IStrategy {
    @Override
    public Action play(Hand hand, Blackjack blackjack) {
        Map<Card, Integer> pack = new HashMap<Card, Integer>();
        for (Card card : PackOfCards.DECK) {
            pack.put(card, 4 * blackjack.NUM_OF_DECKS);
        }
        for (Actor player : blackjack.actors) {
            for (Hand playersHand: player.hands.values()) {
                for (Card card : playersHand.getCards()) {
                    pack.put(card, pack.get(card) - 1);
                }
            }
        }
        for (Card card : blackjack.dealer.hands.get(Blackjack.DEALERS_HAND).getCards()) {
            pack.put(card, pack.get(card) - 1);
        }

        float pSucess = pSucess(hand.getScore(), pack);
        float pDealerSucess = pSucess(blackjack.dealer.hands.get(Blackjack.DEALERS_HAND).getScore(), pack);
        return pSucess * (1 - pDealerSucess) > 0.3 ? Action.HIT : Action.STAND;
    }

    @Override
    public Integer bet(int box) {
        return 1;
    }

    protected float pSucess(int score, Map<Card, Integer> pack) {
        PMF pmfPlayer = new PMF(new Hypothesis[] {Hypothesis.BUST, Hypothesis.NOTBUST});
        for (Card card : pack.keySet()) {
            if (score + card.count < 21 || (card.count2 != null && score + card.count2 < 21)) {
                pmfPlayer.inc(Hypothesis.NOTBUST, pack.get(card));
            } else {
                pmfPlayer.inc(Hypothesis.BUST, pack.get(card));
            }
        }
        pmfPlayer.normalize();

        return pmfPlayer.getP(Hypothesis.NOTBUST);
    }

    private enum Hypothesis {
        BUST, NOTBUST
    }

    private class PMF {
        private Map<Hypothesis, Float> count = new HashMap<Hypothesis, Float>();
        private int totalCount;

        public PMF (Hypothesis[] hypothesises) {
            for(Hypothesis h : hypothesises) {
                this.count.put(h, 0f);
            }
        }

        public void inc(Hypothesis hypothesis, int by) {
            this.count.put(hypothesis, this.count.get(hypothesis) + by);
            this.totalCount += by;
        }

        public void normalize() {
            if (totalCount == 0) {
                return;
            }

            for (Hypothesis h : this.count.keySet()) {
                this.count.put(h, this.count.get(h)/totalCount);
            }
        }

        public float getP(Hypothesis hypothesis) {
            return this.count.get(hypothesis);
        }
    }
}
