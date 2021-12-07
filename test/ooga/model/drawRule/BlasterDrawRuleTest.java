package ooga.model.drawRule;

import java.util.List;
import ooga.model.cards.SkipCard;
import ooga.model.drawRule.blaster.Blaster;
import ooga.model.gameState.GameState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class BlasterDrawRuleTest {
  BlasterDrawRule myRule;

  @Mock
  GameState game;

  @BeforeEach
  public void start(){
    myRule = new BlasterDrawRule();
    game = Mockito.mock(GameState.class);
    when(game.getNextCard()).thenReturn(new SkipCard("red"));
  }

  @Test
  public void noCardsReturnedWhenProbabilityIs0(){
    myRule.setBlastProbability(0);
    Assertions.assertTrue(myRule.forcedDraw(game, 5).isEmpty());
    Assertions.assertTrue(myRule.noPlayDraw(game).isEmpty());
  }

  @Test
  public void allCardsReturnedWhenProbIsOne(){
    myRule.setBlastProbability(1);
    Assertions.assertEquals(5, myRule.forcedDraw(game, 5).size());
    Assertions.assertEquals(1, myRule.noPlayDraw(game).size());
  }

  @Test
  public void returnSizeBuilds(){
    myRule.setBlastProbability(0);
    myRule.forcedDraw(game, 6);
    myRule.noPlayDraw(game);
    myRule.setBlastProbability(1);
    Assertions.assertEquals(8, myRule.noPlayDraw(game).size());
  }

  @Test
  public void sizeResetsAfterBlast(){
    myRule.setBlastProbability(0);
    myRule.forcedDraw(game, 6);
    myRule.noPlayDraw(game);
    myRule.setBlastProbability(1);
    Assertions.assertEquals(8, myRule.noPlayDraw(game).size());
    Assertions.assertEquals(5, myRule.forcedDraw(game, 5).size());
  }

  @Test
  public void whenBlasterProbIs1ItGivesUsOneCardForDrawTillBlast(){
    myRule.setBlastProbability(1);
    Assertions.assertEquals(1, myRule.drawUntilBlast(game, null).size());
  }

  @Test
  public void waitsToInsertUntilColorIsDrawn(){
    myRule.setBlastProbability(1);
    Assertions.assertEquals(1, myRule.drawUntilColor(game, "red").size());
  }

  @Test
  public void returnsCorrectViewCards(){
    myRule.setBlastProbability(0);
    Assertions.assertEquals(0, myRule.getBlasterCards().size());
    myRule.noPlayDraw(game);
    myRule.noPlayDraw(game);
    Assertions.assertEquals(2, myRule.getBlasterCards().size());
    myRule.setBlastProbability(1);
    myRule.noPlayDraw(game);
    Assertions.assertEquals(0, myRule.getBlasterCards().size());
  }

  @Test
  public void blastedWorks(){
    myRule.setBlastProbability(0);
    myRule.noPlayDraw(game);
    assertFalse(myRule.blasted());
    myRule.setBlastProbability(1);
    myRule.noPlayDraw(game);
    assertTrue(myRule.blasted());
    myRule.noPlayDraw(game);
    assertTrue(myRule.blasted());
    myRule.setBlastProbability(0);
    myRule.noPlayDraw(game);
    assertFalse(myRule.blasted());
  }
}
