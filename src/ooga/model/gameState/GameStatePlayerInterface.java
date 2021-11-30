package ooga.model.gameState;

import java.util.Collection;
import ooga.model.cards.Card;
import ooga.model.cards.CardInterface;

public interface GameStatePlayerInterface {

  /**
   * Gets the card(s) required from a no-play-draw
   *
   * @return card(s) from this draw
   */
  public Collection<CardInterface> noPlayDraw();

  /**
   * Returns Whether a card can be played
   *
   * @param cardToPlay card in question
   * @return can or can't play
   */
  public boolean canPlayCard(CardInterface cardToPlay);

  /**
   * sets the lastCardThown member in the GameState
   */
  public void discardCard(Card c);

  /**
   * reverses the order of play
   */
  public void reverseGamePlay();

  /**
   * called by the Skip Action card to skip the next player
   */
  public void skipNextPlayer();

  /**
   * Skips everyone and returns to the current player
   */
  void skipEveryone();

  /**
   * allows class calling this method to set the nextPlayerDrawTwo member to true
   */
  public void addDraw(int drawAmount);

  /**
   * Flips everyone's hands
   */
  void flipCards();
}
