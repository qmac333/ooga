package ooga.model.rules;

import ooga.model.cards.CardInterface;

public interface RuleInterface {

  /**
   * Tells us whether we can play a card given the card that restricts our play
   *
   * @param cardToMatch Card that determines our play
   * @param cardToPlay  Card we are trying to play
   * @return whether we can play this card
   */
  @Deprecated
  boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay);

  /**
   * Tells us whether a card can be played
   *
   * @param cardToMatch Card that determines our play
   * @param cardToPlay Card we want to play
   * @param draw amount we are being told to draw
   * @return whether we can play this card
   */
  boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay, int draw);
}
