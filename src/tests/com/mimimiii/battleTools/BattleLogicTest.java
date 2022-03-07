package com.mimimiii.battleTools;

import com.mimimiii.battleTools.elementTypes.ElementType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.mimimiii.battleTools.elementTypes.ElementType.FIRE;
import static org.junit.jupiter.api.Assertions.*;


class BattleLogicTest {
    private static final Object WATER = ;
    private Object Card;

    // deck vorbereiten. !! [immer geliches deck verwenden. zB 3 deck vorbereiten. ]

    @Test
    void checkValidation() {

        //sachen einrichten von 5 einzelne karten; garantiertes Gewinn
        //for 1st Deck
        Card card1 = new Card("card1", 20.5f, ElementType.WATER);
        Card card2 = new Card("card2", 37.0f, ElementType.FIRE);
        Card card3 = new Card("card3", 10.5f, ElementType.FIRE);
        Card card4 = new Card("card4", 20.5f, ElementType.WATER);
        Card card5 = new Card("card5", 23.5f, ElementType.NORMAL);

//for 2nd Deck
        List<Card> cards = new ArrayList<>();


        //  Card[] cardEmptyDeck = new Card[] {Card name, 0.0f, null};

        /*
        cardEmptyDeck[0] = ["", 0.5f, ElementType. null];
        cardEmptyDeck[0] =["", 0.0f, ElementType. null];
        cardEmptyDeck[0] = ["", 0.0f, ElementType. null];
        cardEmptyDeck[0] = ["", 0.0f, ElementType. null];
        cardEmptyDeck[0] = ["", 0.0f, ElementType. null];


         */

        Deck deck = new Deck("jskidhf", card1, card2, card3, card4, card5);


        boolean result = BattleLogic.checkValidation();
        // tritt der fall ein, dass ich einen Gewinner/ verlierer / und gleichstand bekomme ?

        //evaluate

        // auf

        // 1.
        assertEquals(true, result);


        assertEquals(true, true);

    }


    @Test
    void battle() {
    }

    @Test
    void twoCardFight() {
    }
}