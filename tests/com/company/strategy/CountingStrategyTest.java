package com.company.strategy;

import com.company.Card;
import com.company.PackOfCards;
import com.company.Hand;
import junit.framework.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class CountingStrategyTest {
    @Test
    public void test_Burst() {
        Map<Card, Integer> pack = new HashMap<Card, Integer>();
        for (Card card : PackOfCards.getDeck52()) {
            pack.put(card, 4 * 1);
        }
        Card card = new Card("10", 10);
        pack.put(card, pack.get(card) - 1);
        card = new Card("6", 6);
        pack.put(card, pack.get(card) - 1);
        card = new Card("6", 6);
        pack.put(card, pack.get(card) - 1);

        int bet = 1;
        Hand hand = new Hand("foo", bet);
        hand.addCard(new Card("10", 10));
        hand.addCard(new Card("6", 6));

        float pSucess = new CountingStrategy().pSucess(hand.getScore(), pack);
        Assert.assertEquals(0.32, pSucess, 0.1);
    }

    @Test
    public void test_NoBurst() {
        Map<Card, Integer> pack = new HashMap<Card, Integer>();
        for (Card card : PackOfCards.getDeck52()) {
            pack.put(card, 4 * 1);
        }
        Card card = new Card("5", 5);
        pack.put(card, pack.get(card) - 1);
        card = new Card("8", 8);
        pack.put(card, pack.get(card) - 1);
        card = new Card("10", 10);
        pack.put(card, pack.get(card) - 1);

        Hand hand = new Hand("foo", 1);
        hand.addCard(new Card("5", 5));
        hand.addCard(new Card("8", 8));

        float pSucess = new CountingStrategy().pSucess(hand.getScore(), pack);
        Assert.assertEquals(0.53, pSucess, 0.1);
    }

    @Test
    public void Test() {
        //Betting box 1: Bot's hand: 2(2), Queen(10), 2(2), total : 14
        Map<Card, Integer> pack = new HashMap<Card, Integer>();
        for (Card card : PackOfCards.getDeck52()) {
            pack.put(card, 4 * 1);
        }
        Card card = new Card("7", 7);
        pack.put(card, pack.get(card) - 1);
        card = new Card("6", 6);
        pack.put(card, pack.get(card) - 1);
        card = new Card("6", 6);
        pack.put(card, pack.get(card) - 1);
        card = new Card("King", 10);
        pack.put(card, pack.get(card) - 1);
        card = new Card("3", 3);
        pack.put(card, pack.get(card) - 1);
        card = new Card("9", 9);
        pack.put(card, pack.get(card) - 1);
        card = new Card("Ace", 11);
        pack.put(card, pack.get(card) - 1);
        card = new Card("Jack", 10);
        pack.put(card, pack.get(card) - 1);
        card = new Card("Queen", 10);
        pack.put(card, pack.get(card) - 1);
        card = new Card("9", 9);
        pack.put(card, pack.get(card) - 1);
        card = new Card("4", 4);
        pack.put(card, pack.get(card) - 1);

        Hand hand = new Hand("foo", 1);
        hand.addCard(new Card("6", 6));
        hand.addCard(new Card("6", 6));

        Hand dealer = new Hand("", 1);
        dealer.addCard(new Card("7", 7));
        dealer.addCard(new Card("7", 7));

        float pSucess = new CountingStrategy().pSucess(hand.getScore(), pack);
        float pDealer = new CountingStrategy().pSucess(dealer.getScore(), pack);
        System.out.println(pSucess * (1 - pDealer));
        Assert.assertEquals(0.63, pSucess, 0.1);
        Assert.assertEquals(0.46, pDealer, 0.1);
    }
}