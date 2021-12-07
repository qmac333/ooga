package ooga.model.player.playerGroup;

import java.util.Collection;
import ooga.model.cards.CardInterface;
import ooga.model.instanceCreation.ReflectionErrorException;

public interface PlayerGroupPlayerInterface {

  /**
   * Causes the player who played this card to be up again
   */
  void skipEveryone();

  /**
   * Causes the next player in line to be skipped
   */
  void skipNextPlayer();

  /**
   * Reverses the order of the game
   */
  void reverseOrder();

  /**
   * Causes everyone to flip their hands
   */
  void flipGame();

  /**
   * Adds the correct draw to be adhered to in the GameState
   *
   * @param drawAmount Drawing code to set
   */
  void enforceDraw(int drawAmount);

  /**
   * Discards the card for a player
   *
   * @param card Card to discard
   */
  void discardCard(CardInterface card);

  /**
   * Returns whether a card can be played
   *
   * @param card Card to play
   * @return Whether it's a valid move
   */
  boolean canPlayCard(CardInterface card);

  /**
   * Performs a draw for the player
   *
   * @return The cards returned by the draw
   */
  Collection<CardInterface> noPlayDraw() throws ReflectionErrorException;
}
