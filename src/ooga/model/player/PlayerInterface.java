package ooga.model.player;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import ooga.model.cards.CardInterface;
import ooga.model.cards.ViewCardInterface;

/**
 * Interface implemented by Players in a game.  Interacts with the GameState and Cards.
 */
public interface PlayerInterface {


  /**
   * player plays a card in its hand and calls the card's action implementation will depend on
   * whether the player is automated or not
   */
  void playCard();

  /**
   * Sets the player classes suppliers
   *
   * @param integerSupplier supplier to get card index
   * @param stringSupplier supplier to get color
   */
  void setSuppliers(Supplier<Integer> integerSupplier, Supplier<String> stringSupplier);

  /**
   * add a list of cards to a player's hand, specifically at the end
   *
   * @param cards cards to add
   */
  void addCards(Collection<CardInterface> cards);

  /**
   * returns the name of the player
   *
   * @return a String that is the player's name
   */
  String getName();

  /**
   * returns the total number of points that a player's hand contains
   *
   * @return integer sum of card nums
   */
  int getNumPoints();

  /**
   * @return The size of the player's hand
   */
  int getHandSize();

  List<ViewCardInterface> getViewCards();

  /**
   * Flips all of their cards
   */
  void flipHand();

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

  /**
   * @return The indexes in the players hand that they can play
   */
  Collection<Integer> getValidIndexes();

  /**
   * Gives points to the player
   *
   * @param amount Number of points to give
   */
  void awardPoints(int amount);

  /**
   * @return the amount of points the player has
   */
  int getPoints();
}
