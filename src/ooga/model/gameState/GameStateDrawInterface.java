package ooga.model.gameState;

import ooga.model.cards.Card;
import ooga.model.cards.CardInterface;

public interface GameStateDrawInterface {

  /**
   * Gets the next card from the deck
   *
   * @return card at the top of the deck
   */
  CardInterface getNextCard();

  /**
   * Determines whether you can play a card
   *
   * @param cardToPlay the card we are trying to play
   * @return whether this is a legal move
   */
  boolean canPlayCard(CardInterface cardToPlay);
}
