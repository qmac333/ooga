package ooga.model.gameState;

import ooga.model.deck.UnoDeck;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TwoSidedDeckTest {


    @Test
    public void initializeTwoSidedDeckTest(){
        UnoDeck twoSidedCardsDeck = new UnoDeck("Flip");
        UnoDeck normalDeck = new UnoDeck("Basic");

        assertTrue(twoSidedCardsDeck.getNumCards() > normalDeck.getNumCards());
    }
}
