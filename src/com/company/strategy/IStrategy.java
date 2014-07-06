package com.company.strategy;

import com.company.Action;
import com.company.Hand;
import com.company.Blackjack;

/**
 * Created by Daria on 2014-07-05.
 */
public interface IStrategy {
    public Action play(Hand hand, Blackjack blackjack);
    public Integer bet(int box);
}
