package com.company;

import org.junit.Test;

import static org.junit.Assert.*;

public class PackOfCardsTest {

    @Test
    public void test_shuffle() {
        int i = 0;
        PackOfCards p = new PackOfCards(1);
        p.shuffle();
        while (true) {
            Card c = p.takeNext();
            if (c == null) {
                break;
            }
            System.out.println(c);
            i++;
        }
        assertEquals(52, i);
    }
}