package ooga.model.gameState;

import java.util.Collection;
import ooga.model.cards.CardInterface;

public interface GameStatePlayerInterface {

  /**
   * Gets the card(s) required from a no-play-draw
   *
   * @return card(s) from this draw
   */
  Collection<CardInterface> noPlayDraw();

  /**
   * Returns Whether a card can be played
   *
   * @param cardToPlay card in question
   * @return can or can't play
   */
  boolean canPlayCard(CardInterface cardToPlay);

  /**
   * sets the lastCardThown member in the GameState
   */
  void discardCard(CardInterface c);

  /**
   * reverses the order of play
   */
  void reverseGamePlay();

  /**
   * called by the Skip Action card to skip the next player
   */
  void skipNextPlayer();

  /**
   * Skips everyone and returns to the current player
   */
  void skipEveryone();

  /**
   * allows class calling this method to set the nextPlayerDrawTwo member to true
   */
  void addDraw(int drawAmount);

  /**
   * Flips everyone's hands
   */
  void flipCards();
}
