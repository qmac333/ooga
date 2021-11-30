package ooga.model.deck;

import ooga.model.cards.Card;
import ooga.model.cards.CardInterface;
import ooga.model.cards.ViewCardInterface;

/**
 * An interface that passes read-only information about a card pile to the view
 */
public interface CardPileViewInterface {

  /**
   * gets the number of cards in a pile
   */
  public int getNumCards();


  /**
   * allows caller to see the card at the top of a
   * pile of cards
   */
  public Card lastCardPushed();


}
