package ooga.model.cards;

import ooga.model.gameState.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActionCardTests extends DukeApplicationTest {

    GameState game;

    @BeforeEach
    public void start(){game = new GameState();}

    @Test
    public void drawFourCardTest(){
        ActionCard dfc = new DrawFourCard(game, "red", "dfc");
        dfc.executeAction();
        assertEquals("dfc", game.getLastCardThrownType());
    }

    @Test
    public void drawTwoCardTest(){
        ActionCard dtc = new DrawTwoCard(game, "red", "dtc");
        dtc.executeAction();
        assertEquals("dtc", game.getLastCardThrownType());
    }

    
}
