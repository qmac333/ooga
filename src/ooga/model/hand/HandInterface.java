package ooga.model.hand;

import java.util.Collection;
import ooga.model.cards.CardInterface;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.playerGroup.PlayerGroupPlayerInterface;
import ooga.model.player.player.PlayerCardInterface;

public interface HandInterface {

  /**
   * Adds list of cards to the hand
   *
   * @param card Card to add
   */
  void add(Collection<CardInterface> card);

  /**
   * Plays a card
   *
   * Deprecated in favor of the version that also takes a player
   *
   * @param indexOfCard The index of the card to play
   */
  @Deprecated
  void play(int indexOfCard, GameStatePlayerInterface game) throws InvalidCardSelectionException;

  /**
   * Plays a card in the hand
   *
   * @param indexOfCard index of the card to play
   * @param group Group to affect
   * @param player player playing it
   * @throws InvalidCardSelectionException if the card is unplayable
   */
  void play(int indexOfCard, PlayerGroupPlayerInterface group, PlayerCardInterface player) throws InvalidCardSelectionException;

  /**
   * Flips all the cards in the hand
   */
  void flip();

  /**
   * @return the size of the hand
   */
  int size();

  /**
   * Removes all cards of that color from the hand
   *
   * @param color to discard
   */
  Collection<CardInterface> removeColor(String color);
}
