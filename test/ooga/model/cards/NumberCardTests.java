package ooga.model.cards;

import ooga.model.gameState.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberCardTests extends DukeApplicationTest {

    GameState game;

    @BeforeEach
    public void start(){game = new GameState(null, null, 100, false);}

    @Test
    public void numberCardTest(){
        Card nc = new NumberCard(game, "red", 1);
        nc.executeAction();
        assertEquals("Number", game.getLastCardThrownType());
    }
}
