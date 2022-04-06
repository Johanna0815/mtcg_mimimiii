package com.mimimiii.battleTools.elementTypes;

public class StringToEnumConverter {
    public static ElementType getElementType(String element) {
        switch (element.toUpperCase()) {
            case "FIRE":
                return ElementType.FIRE;
            case "WATER":
                return ElementType.WATER;
            case "NORMAL":
                return ElementType.NORMAL;
        }
        return null;
    }

    public static CardType getCardType(String type) {

        switch (type.toUpperCase()) {
            case "GOBLIN":
                return CardType.GOBLIN;
            case "DRAGON":
                return CardType.DRAGON;
            case "WIZZARD":
                return CardType.WIZZARD;
            case "ORK":
                return CardType.ORK;
            case "KNIGHT":
                return CardType.KNIGHT;
            case "WATERSPELL":
                return CardType.WATERSPELL;
            case "KRAKEN":
                return CardType.KRAKEN;
            case "FIREELF":
                return CardType.FIREELF;

        }
        return null;
    }
}