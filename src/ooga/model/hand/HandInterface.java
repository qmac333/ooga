package ooga.model.hand;

import java.util.List;
import ooga.model.cards.CardInterface;
import ooga.model.gameState.GameStatePlayerInterface;

public interface HandInterface {

  /**
   * Adds list of cards to the hand
   *
   * @param card Card to add
   */
  void add(List<CardInterface> card);

  /**
   * Plays a card
   *
   * @param indexOfCard The index of the card to play
   */
  void play(int indexOfCard, GameStatePlayerInterface game) throws InvalidCardSelectionException;

  /**
   * Flips all the cards in the hand
   */
  void flip();

  /**
   * @return the size of the hand
   */
  int size();
}
