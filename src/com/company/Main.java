package com.company;

import com.company.strategy.CountingStrategy;
import com.company.strategy.StdIOStrategy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        Actor you = new Actor("You")
                .setStrategy(new StdIOStrategy())
                .setBudget(100);
        Actor bot = new Actor("Bot")
                .setStrategy(new CountingStrategy())
                .setBudget(100);
	    Blackjack blackjack = new Blackjack(new LinkedList<Actor>(Arrays.asList(you, bot)));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String inputLine = "";
        try {
            while (!inputLine.equals("N")) {
                blackjack.play();
                System.out.println("One more time? N(o) to exit.");
                inputLine = br.readLine();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
