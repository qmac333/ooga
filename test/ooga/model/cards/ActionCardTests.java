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
    public void start(){game = new GameState(null, null, 100, false);}

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

    @Test
    public void reverseCardTest(){

        ActionCard rc = new ReverseCard(game, "red", "rc");
        rc.executeAction();
        assertEquals(-1, game.getOrder());
    }

    @Test
    public void skipCardTest(){
        ActionCard sc = new SkipCard(game, "red", "sc");
        sc.executeAction();
        assertEquals("sc", game.getLastCardThrownType());
    }

    @Test
    public void wildCardTest(){
        ActionCard wc = new WildCard(game, "wild", "wc");
        wc.setCardColor("red"); // will need to change this line of code with advent of consumerInterface
        assertEquals("red", wc.getColor());
    }


}
