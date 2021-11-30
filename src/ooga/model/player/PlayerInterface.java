package ooga.model.player;

import java.util.Collection;
import java.util.List;
import ooga.model.cards.Card;
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
  public void playCard();

  /**
   * add a list of cards to a player's hand, specifically at the end
   *
   * @param cards cards to add
   */
  public void addCards(Collection<CardInterface> cards);

  /**
   * returns the name of the player
   *
   * @return a String that is the player's name
   */
  public String getName();

  /**
   * returns the total number of points that a player's hand contains
   *
   * @return integer sum of card nums
   */
  public int getNumPoints();

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
}
