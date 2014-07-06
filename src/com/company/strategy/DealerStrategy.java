package com.company.strategy;

import com.company.*;
import com.company.Hand;

/**
 * Created by Daria on 2014-07-05.
 */
public class DealerStrategy implements IStrategy {
    @Override
    public Action play(Hand hand, Blackjack blackjack) {
        Action result;
        int dealerScore = blackjack.dealer.hands.get(Blackjack.DEALERS_HAND).getScore();

        if (dealerScore < 17 ) {
            result = Action.HIT;
        } else {
            result = Action.STAND;
        }
        return result;
    }

    @Override
    public Integer bet(int box) {
        throw new UnsupportedOperationException();
    }
}
