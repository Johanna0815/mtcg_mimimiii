package com.mimimiii.battleTools.battle;

import com.fasterxml.jackson.core.TreeNode;
import com.mimimiii.battleTools.battle.Card;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Deck {
    // private static final String OWNER = ;
    //meine konstante:
    private String owner;

    private LinkedList<Card> deckList;

    //...methode nachschauen!// ... sagt ein oder mehrere  // erstellt ein Array, gleiche datentyp nötig !
    public Deck(String owner, Card... cards) {

        this.owner = owner;
        this.deckList = new LinkedList<Card>(); // arrrayList tal vez mejor
        // this.Cards = cards;

        for (Card c : cards) {
            deckList.add(c);
        }
    }

    public String getOwner() {
        return owner;
    }







    public void setOwner(String owner) {
        this.owner = owner;
    }

    public LinkedList<Card> getDeckList() {
        return deckList;
    }

    public void setDeckList(LinkedList<Card> deckList) {
        this.deckList = deckList;
    }


    public Card putOutCard() {
        try {
            return deckList.pop();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        return null;
    }


    // check if 5 cards, für antritt; deckLänge bestimmen; muss 5 card haebn
    // muss checken, wann deck verloren hat, deck.length null;
    // decklänge zurück geben.
    public int decklength() {
        return deckList.size();
    }


    // abgeäändert auf all ändern ---
    public void putSingleCard(Card... cards) {

        for (Card c : cards) {
            deckList.add(c);
        }


    }


    public boolean isEmpty() {
        return deckList.isEmpty();
    }


//double


    public float getRandomCard() {
//
        float implizitesCast = (float) (Math.random() * 8); // kartenanzahl anpassen !
        System.out.println(implizitesCast);

        return implizitesCast;
    }


    public double getSize() {
        return 0;
    }

    public void removeCard(Card card2) {
    }

    @Override
    public String toString() {
        return "Deck{" +
                "owner='" + owner + '\'' +
                ", deckList=" + deckList +
                '}';
    }






    public void addID(Card id) {
        Card.deckList.add(id);
    }
    public LinkedList<String> getList() {
        return decklist;
    }
    public void addID(String id) {
        deckList.add(id);
    }


    public TreeNode deckList() {
        return decklist;
    }
}
