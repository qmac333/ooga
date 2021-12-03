package ooga.model.drawRule;

import java.util.Collection;
import ooga.model.cards.CardInterface;
import ooga.model.gameState.GameState;
import ooga.model.gameState.GameStateDrawInterface;

public interface DrawRuleInterface {

  /**
   * This is called when a player need to draw because they don't have a valid card to play
   *
   * @param game the game this is happening in
   * @return the card(s) that are drawn as a result
   */
  Collection<CardInterface> noPlayDraw(GameStateDrawInterface game);

  /**
   * This is called as the result of draw penalty from a player
   *
   * @param game   the game this is happening in
   * @param amount the amount you need to draw
   * @return the card(s) that are drawn as a result
   */
  Collection<CardInterface> forcedDraw(GameStateDrawInterface game, int amount);

  /**
   * Forces the player to draw until the blaster goes off
   *
   * @param game Game we draw from
   * @return cards that are eventually ejected
   */
  Collection<CardInterface> drawUntilBlast(GameStateDrawInterface game);

  /**
   * Draws until we get a card that matches the color of the top card on the discard pile
   *
   * @param game  Game that we are drawing from
   * @param color Color we need to match
   * @return resulting cards
   */
  Collection<CardInterface> drawUntilColor(GameStateDrawInterface game, String color);

  /**
   * Sets the probability of the blaster if any
   *
   * @param probability desired probability
   */
  void setBlastProbability(double probability);
}
