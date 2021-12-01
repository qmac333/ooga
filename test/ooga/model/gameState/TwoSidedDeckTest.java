package ooga.model.gameState;

import ooga.model.deck.UnoDeck;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TwoSidedDeckTest {


    @Test
    public void initializeTwoSidedDeckTest(){
        UnoDeck twoSidedCardsDeck = new UnoDeck("TwoSided", new HashMap<>());
        UnoDeck normalDeck = new UnoDeck("Basic", new HashMap<>());
        
        assertTrue(twoSidedCardsDeck.getNumCards() > normalDeck.getNumCards());
    }
}
