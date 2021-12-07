package ooga.model.player.playerGroup;

import java.util.List;
import java.util.Map;
import ooga.model.hand.Hand;
import ooga.model.instanceCreation.ReflectionErrorException;
import ooga.model.player.player.PlayerGameInterface;

public interface PlayerGroupGameInterface extends Iterable<PlayerGameInterface> {

  /**
   * @return The current player
   */
  PlayerGameInterface getCurrentPlayer();

  /**
   * Causes current player to play a turn
   */
  void playTurn() throws ReflectionErrorException;

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

  /**
   * Count points left in the game and award to the player with no cards
   */
  void countAndAwardPoints();


  // CHEAT KEYS

  /**
   * Changes the player's to 7 number cards
   *
   * @param color Color of the cards
   * @param number Number of the cards
   */
  void seven(String color, String number);

  /**
   * Changes all but the ignored cards to a certain color
   *
   * @param color Color to change to
   * @param colorToIgnore Color that we are ignoring
   */
  void toColor(String color, String colorToIgnore);

  /**
   * Changes the game to the state where you can win
   *
   * @param color Color of the card you'll have
   * @param number Number of the card you'll have
   */
  void toWin(String color, String number);

  /**
   * Changes the game to the state where you can get uno
   *
   * @param color Color of the card you'll have
   * @param number Number of the card you'll have
   */
  void toUno(String color, String number);

  /**
   * Adds an action card to the current player's hand
   *
   * @param type Type of action card
   * @param color Color of card
   * @throws ReflectionErrorException When reflection doesn't work properly
   */
  void addCard(String type, String color) throws ReflectionErrorException;
}
