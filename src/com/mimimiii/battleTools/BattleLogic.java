package com.mimimiii.battleTools;

public class BattleLogic {

    // nur ablauf ! -> static (ohne objekt davon )!
    public static boolean checkValidation(Deck deck) {
        if (deck.isEmpty()) {
            return false;
        } else if (deck.decklength() != 5) {
            return false;
        }
        return true;
    }


    // throws Exception
    public static BattleMatchResult battle(Deck deckFirst, Deck deckSecond) {

        if (!checkValidation(deckFirst) || !checkValidation(deckSecond)) {

            // throw custumException
            return null;
        }

        int counter = 0;

        do {
            counter++;

            twoCardFight(deckFirst, deckSecond);

        }
        while (counter <= 100 && !deckFirst.isEmpty() && !deckSecond.isEmpty());


 BattleMatchResult result = new BattleMatchResult();

        if (deckFirst.isEmpty()) {
            System.out.println("DeckA has lost.");
            result.setLooser(deckFirst.getOwner());
            result.setWinner(deckSecond.getOwner());
            result.setRemis(false);

        } else if (deckSecond.isEmpty()) {
            System.out.println("DeckB has lost.");
            result.setLooser(deckSecond.getOwner());
            result.setWinner(deckFirst.getOwner());
            result.setRemis(false);
        } else {
            System.out.println(" Both lost....Remis!");

            result.setRemis(true);

        }

return result;





    }


    //offengelegt
    //verglichen
    //neu verteilt
    public static void twoCardFight(Deck deckFirst, Deck deckSecond) {

        Card cardA = deckFirst.putOutCard();
        Card cardB = deckSecond.putOutCard();



        /*
battle log element Probleme fehleen noch!
elemenete fehlen noch !

 */
        if (cardA.getDamage() > cardB.getDamage()) {

            deckFirst.putSingleCard(cardA, cardB);
            // deckFirst.putSingleCard(cardB);

            return;
        } else if (cardA.getDamage() < cardB.getDamage()) {

            deckSecond.putSingleCard(cardA, cardB);
            return;
        } else if (cardA.getDamage() == cardB.getDamage()) {

            deckFirst.putSingleCard(cardA);
            deckSecond.putSingleCard(cardB);
            return;

        }





    }


}


