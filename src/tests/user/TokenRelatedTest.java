package user;

import com.mimimiii.battleTools.user.TokenRelated;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenRelatedTest {

// curl adapt para admin
// kienboec-mtcgToken -pass
    @Test
    void getUserToken() {
        String token = "TestPersona-mtcgToken";
        assertEquals(0,token.compareTo(TokenRelated.getUserToken("TestPersona")));
    }
}
