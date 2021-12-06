package ooga.model.player.playerGroup;

import java.util.List;
import java.util.Map;
import ooga.model.hand.Hand;
import ooga.model.player.player.PlayerGameInterface;

public interface PlayerGroupGameInterface extends Iterable<PlayerGameInterface> {

  /**
   * @return The current player
   */
  PlayerGameInterface getCurrentPlayer();

  /**
   * Causes current player to play a turn
   */
  void playTurn();

  /**
   * Adds player to the game
   *
   * @param player Player to add
   */
  void addPlayer(PlayerGameInterface player);

  /**
   * @return Index of Player whose turn it is (Needed for saving)
   */
  int getCurrentPlayerIndex();

  /**
   * @return Map of Player Name to their Type (Needed for saving)
   */
  Map<String, String> getPlayerMap();

  /**
   * @return whether the current player is a human
   */
  boolean userPicksCard();

  /**
   * Sets which order the game is moving (Needed For loading)
   *
   * @param order Order of the game
   */
  void setOrder(int order);

  /**
   * Sets which player to start at (For Loading)
   *
   * @param player Index of player to start at
   */
  void setCurrent(int player);

  /**
   * Loads all the hands into the players
   *
   * @param handsToLoad Hands we want to give
   *                    <p>
   *                    Needed for loading a game
   */
  void loadHands(List<Hand> handsToLoad);

  /**
   * @return The order of the game
   * <p>
   * Needed to save Game
   */
  int getMyOrder();

  /**
   * Sets whether uno has been called
   *
   * @param called True/False
   */
  void setUnoCalled(boolean called);

  /**
   * Changes current player to the next one we need
   */
  void loadNextPlayer();

  void countAndAwardPoints();
}
