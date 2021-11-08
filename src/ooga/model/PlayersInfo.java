package ooga.model;

import java.util.List;

/**
 * A variable type that encapsulates information
 * about the current players in the game,
 * as well as which player is currently taking their turn,
 * and the direction of play (counterclockwise, or clockwise).
 *
 */
public interface PlayersInfo {

  static final int CLOCKWISE = 0;
  static final int COUNTERCLOCKWISE = 1;

  /**
   * @return a list of all player names
   * list can be modified by the calling class, is not an alias of any object in the model
   */
  List<String> getPlayerNames();

  /**
   * @return the index of the player who is currently taking their turn from the list above.
   */
  int getCurrentPlayer();

  /**
   * @return the direction of play (either CLOCKWISE or COUNTERCLOCKWISE)
   */
  int getPlayDirection();


}
