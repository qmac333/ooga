package ooga.model;

import java.util.Collection;
import ooga.model.cards.Card;

/**
 * Interface implemented by Players in a game.  Interacts with the GameState and Cards.
 */
public interface PlayerInterface {


  /**
   * player plays a card in its hand and calls the card's action implementation will depend on
   * whether the player is automated or not
   */
  public void playCard(int i);

  /**
   * adds a card to a player's hand, specifically at the end
   * @param card
   */
  public void addCard(Card card);

  /**
   * returns a players hand
   *
   * @return Collection of a players cards
   */
  public Collection<Card> getHand();

  /**
   * returns the name of the player
   *
   * @return a String that is the player's name
   */
  public String getName();
}
