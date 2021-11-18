package ooga.model.gameState;

import java.util.List;
import ooga.model.cards.ViewCardInterface;

/**
 * An interface that allows the view to interact with the game state, but in a read-only way.
 */
public interface GameStateViewInterface {

  public static final int CLOCKWISE = 0;
  public static final int COUNTERCLOCKWISE = 1;

  /**
   * @return a unmodifiable list of all player names
   */
  List<String> getPlayerNames();

  /**
   * @return an unmodifiable list of card counts in each player's hand
   */
  List<Integer> getCardCounts();

  /**
   * return an integer corresponding to the direction of gameplay (CLOCKWISE vs COUNTERCLOCKWISE)
   */
  public int getGameplayDirection();

  /**
   * returns the player index whose turn it is to play
   *
   * @return index of the current player
   */
  public int getCurrentPlayer();

  /**
   * returns a map of card types to the different Colors and Types of cards
   * that exist in a players hand with that number
   * @return a Map of Integers to Lists of Strings
   */
  public List<ViewCardInterface> getCurrentPlayerCards();

  /**
   * Plays a turn of the game.
   */
  public void playTurn();

}
