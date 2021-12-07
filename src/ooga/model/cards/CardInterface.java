package ooga.model.cards;


import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.player.PlayerCardInterface;

/**
 * interface implemented by all Cards in a game. changes the GameState and/or player that holds the
 * card
 */
public interface CardInterface {

  /**
   * Executes whatever the card's action is on the GameState and/or player which holds the card.
   * This can include identifying cards that can be played in the case of a Number Card, or changing
   * the GameState in place of an Action Card.
   *
   * This has been deprecated in favor of the version that uses a player
   */
  @Deprecated
  void executeAction(GameStatePlayerInterface game);

  /**
   * Has the player execute the action specified by the card
   *
   * @param player The player playing the card
   */
  void executeAction(PlayerCardInterface player);

  /**
   * @return the type of the card
   */
  String getType();

  /**
   * @return the number of the card
   */
  int getNum();

  /**
   * @return the color of the card
   */
  String getMyColor();

  /**
   * Flips the card
   */
  void flip();

  /**
   * Checks to see if two CardInterfaces are equal - TESTING PURPOSES ONLY
   * @param other the CardInterface to compare this to
   * @return boolean indicating equality
   */
  @Override
  boolean equals(Object other);

  /**
   * Sets a card's color to that specified. Used for cheat keys.
   *
   * @param color Color to switch to
   */
  void setColor(String color);
}
