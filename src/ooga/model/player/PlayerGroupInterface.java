package ooga.model.player;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public interface PlayerGroupInterface {

  /**
   * @return A list of each player's points
   */
  List<Integer> getPoints();

  /**
   * @return The indexes of cards that can be played by the current Player
   */
  Collection<Integer> getCurrentPlayerValidIndexes();

  /**
   * Causes the player who played this card to be up again
   */
  void skipEveryone();

  /**
   * Causes the next player in line to be skipped
   */
  void skipNextPlayer();

  /**
   * Reverses the order of the game
   */
  void reverseOrder();

  /**
   * Causes everyone to flip their hands
   */
  void flipGame();

  /**
   * Adds the correct draw to be adhered to in the GameState
   *
   * @param drawAmount Drawing code to set
   */
  void enforceDraw(int drawAmount);

  /**
   * @return A list that has the hand Size of each Player
   */
  List<Integer> getHandSizes();

  /**
   * @return A list that has the names of each player
   */
  List<String> getNames();

  /**
   * Sets the suppliers that will be used for the user input
   *
   * @param integerSupplier Supplier to get the Index of card to Play
   * @param stringSupplier Supplier to get Color to pick
   */
  void setSuppliers(Supplier<Integer> integerSupplier, Supplier<String> stringSupplier);

  /**
   * Causes current player to play a turn
   */
  void playTurn();
}
