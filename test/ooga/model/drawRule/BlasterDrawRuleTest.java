package ooga.model.drawRule;

import ooga.model.cards.SkipCard;
import ooga.model.gameState.GameState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

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
}
