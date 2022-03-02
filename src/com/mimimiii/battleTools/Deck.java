package com.mimimiii.battleTools;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Deck {
    //meine konstante:
    private String owner;

    private LinkedList<Card> deckList;


    //...methode nachschauen!// ... sagt ein oder mehrere  // erstellt ein Array, gleiche datentyp nötig !
    public Deck(String owner, Card... cards) {

        this.owner = owner;
        this.deckList = new LinkedList<Card>();
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


    public void putSingleCard(Card card) {
        deckList.add(card);

    }






    public boolean isEmpty() {
        return deckList.isEmpty();
    }


}
