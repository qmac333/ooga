package ooga.model.gameState;

import ooga.model.cards.Card;

public interface GameStatePlayerInterface {
  /**
   * Gets the next card from the deck
   *
   * @return card at the top of the deck
   */
  public Card getNextCard();

  /**
   * Returns Whether a card can be played
   *
   * @param cardToPlay card in question
   * @return can or can't play
   */
  public boolean canPlayCard(Card cardToPlay);
}
