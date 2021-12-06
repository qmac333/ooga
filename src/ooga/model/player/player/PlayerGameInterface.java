package ooga.model.player.player;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import ooga.model.cards.CardInterface;
import ooga.model.cards.ViewCardInterface;
import ooga.model.hand.Hand;

public interface PlayerGameInterface {

  /**
   * Removes all cards from the players hand
   */
  void dumpCards();

  /**
   * @return Player's Hand
   *
   * Used for Saving
   */
  Hand getMyHand();

  /**
   * @return the amount of points the player has
   */
  int getPoints();

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
   * Loads in a players hand
   * @param hand Hand to load
   *
   * Used for loading
   */
  void loadHand(Hand hand);

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
}
