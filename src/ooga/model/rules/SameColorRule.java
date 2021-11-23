package ooga.model.rules;

import ooga.model.cards.CardInterface;

public class SameColorRule implements RuleInterface {

  @Override
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay) {
    return cardToMatch.getMyColor().equals(cardToPlay.getMyColor());
  }
}
