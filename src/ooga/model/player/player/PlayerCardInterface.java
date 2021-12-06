package ooga.model.player.player;

/**
 * Interface implemented by Players in a game.  Interacts with the GameState and Cards.
 */
public interface PlayerCardInterface {

  /**
   * Gets the color the player wants to change the deck to
   *
   * @return the color
   */
  String getColor();

  /**
   * Adds a specified draw amount to the game
   *
   * @param drawAmount amount to add
   */
  void enforceDraw(int drawAmount);

  /**
   * Causes the entire game to flip
   */
  void flipGame();

  /**
   * Causes the game to reverse order
   */
  void reverseGame();

  /**
   * Causes the game to skip the next player
   */
  void skipNextPlayer();

  /**
   * Causes the game to skip everyone
   */
  void skipEveryone();

  /**
   * Has player discard all cards that are of the specified color
   *
   * @param color color to discard
   */
  void discardColor(String color);
}
