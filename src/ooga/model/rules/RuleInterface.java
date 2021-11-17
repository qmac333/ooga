package ooga.model.rules;

import ooga.model.cards.Card;

public interface RuleInterface {

  /**
   * Tells us whether we can play a card given the card that restricts our play
   *
   * @param cardToMatch Card that determines our play
   * @param cardToPlay  Card we are trying to play
   * @return whether we can play this card
   */
  boolean canPlay(Card cardToMatch, Card cardToPlay);
}