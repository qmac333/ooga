package ooga.model.cards;


import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.Player;

/**
 * interface implemented by all Cards in a game. changes the GameState and/or player that holds the
 * card
 */
public interface CardInterface {

  /**
   * Executes whatever the card's action is on the GameState and/or player which holds the card.
   * This can include identifying cards that can be played in the case of a Number Card, or changing
   * the GameState in place of an Action Card.
   */
  void executeAction(GameStatePlayerInterface game);


  /**
   * returns a card's type member
   *
   * @return
   */
  String getType();


  /**
   * returns a cards number
   *
   * @return
   */
  int getNum();


  /**
   * returns a card's color
   *
   * @return
   */
  String getMyColor();
}
