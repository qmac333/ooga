package ooga.model.gameState;

import java.util.List;
import ooga.model.cards.Card;

public interface GameStatePlayerInterface {
  /**
   * Gets the card(s) required from a no-play-draw
   *
   * @return card(s) from this draw
   */
  public List<Card> noPlayDraw();

  /**
   * Returns Whether a card can be played
   *
   * @param cardToPlay card in question
   * @return can or can't play
   */
  public boolean canPlayCard(Card cardToPlay);

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
   * allows class calling this method to set the nextPlayerDrawTwo member to true
   */
  public void addDraw(int drawAmount);
}
