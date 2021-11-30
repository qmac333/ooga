package ooga.model.cards;

import java.util.HashMap;
import ooga.model.gameState.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberCardTests extends DukeApplicationTest {

    GameState game;

    @BeforeEach
    public void start(){game = new GameState("Basic", new HashMap<>(), 100, false);}

    @Test
    public void numberCardTest(){
        OneSidedCard nc = new NumberCard("red", 1);
        nc.executeAction(game);
        assertEquals("Number", game.getLastCardThrownType());
    }
}
