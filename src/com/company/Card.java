package com.company;

/**
 * Created by Daria on 2014-07-05.
 * In this game we don't care about card's suit.
 */
public class Card {
    public final String face;
    public final int count;
    public final Integer count2;

    public Card(String face, int count, Integer count2) {
        this.face = face;
        this.count = count;
        this.count2 = count2;
    }

    public Card(String face, int count) {
        this(face, count, null);
    }

    @Override
    public String toString() {
        return this.count2 == null ?
                String.format("%s(%d)", this.face, this.count) :
                String.format("%s(%d/%d)", this.face, this.count, this.count2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (!face.equals(card.face)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return face.hashCode();
    }
}
