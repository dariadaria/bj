package com.company.strategy;

import com.company.Action;
import com.company.Hand;
import com.company.Blackjack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Daria on 2014-07-05.
 */
public class StdIOStrategy implements IStrategy {
    @Override
    public Action play(Hand hand, Blackjack blackjack) {
        Action result = Action.STAND;

        if(hand.getScore() >= 21) {
            return result;
        }

        System.out.println(String.format("Play %s. Hit(h), Stand(s), Play Dead(q)?", hand.id));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            String inputLine = br.readLine().trim();
            while (!(inputLine.equals("h") || inputLine.equals("s") || inputLine.equals("q"))) {
                System.out.println(String.format("%s is not an option.", inputLine));
                inputLine = br.readLine();
            }
            if (inputLine.equals("h")) {
                result = Action.HIT;
            } else if (inputLine.equals("s")) {
                result = Action.STAND;
            } else {
                throw new IllegalStateException("Someone is cheating here");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Integer bet(int box) {
        System.out.println(String.format("Your bet for box %d: ", box));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Integer bet = null;
        try {
            bet = Integer.valueOf(br.readLine().trim());
        } catch (Exception e) {
            bet = null;
        }
        return bet;
    }
}
