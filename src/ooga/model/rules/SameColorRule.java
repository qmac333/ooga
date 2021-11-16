package ooga.model.rules;

import ooga.model.cards.Card;

public class SameColorRule implements RuleInterface {

  @Override
  public boolean canPlay(Card cardToMatch, Card cardToPlay) {
    return cardToMatch.getMyColor().equals(cardToPlay.getMyColor());
  }
}
