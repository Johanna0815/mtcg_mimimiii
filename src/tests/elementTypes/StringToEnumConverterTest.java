package elementTypes;

import com.mimimiii.battleTools.elementTypes.CardType;
import com.mimimiii.battleTools.elementTypes.StringToEnumConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringToEnumConverterTest {


    @Test
    void getElementType() {

        String water = "WATER";
        String fire = "FIRE";
        String normal = "NORMAL";

        //Assertion stable
        assertEquals(0, water.compareTo(StringToEnumConverter.getElementType("water").toString()));
        assertEquals(0, fire.compareTo(StringToEnumConverter.getElementType("fire").toString()));
        assertEquals(0, normal.compareTo(StringToEnumConverter.getElementType("normal").toString()));
    }


    @Test
    void getCardType() {
        String goblin = "GOBLIN";
        String dragon = "DRAGON";
        String wizzard = "WIZZARD";
        String ork = "ORK";
        String knight = "KNIGHT";
        String waterspell = "WATERSPELL";
        String kraken = "KRAKEN";
        String fireelf = "FIREELF";

        assertEquals(0, goblin.compareTo(StringToEnumConverter.getCardType("GOBLIN").toString()));
        assertEquals(0, dragon.compareTo(StringToEnumConverter.getCardType("DRAGON").toString()));
        assertEquals(0, wizzard.compareTo(StringToEnumConverter.getCardType("WIZZARD").toString()));
        assertEquals(0, ork.compareTo(StringToEnumConverter.getCardType("ORK").toString()));
        assertEquals(0, knight.compareTo(StringToEnumConverter.getCardType("KNIGHT").toString()));
        assertEquals(0, waterspell.compareTo(StringToEnumConverter.getCardType("WATERSPELL").toString()));
        assertEquals(0, kraken.compareTo(StringToEnumConverter.getCardType("KRAKEN").toString()));
        assertEquals(0, fireelf.compareTo(StringToEnumConverter.getCardType("FIREELF").toString()));

    }
}
