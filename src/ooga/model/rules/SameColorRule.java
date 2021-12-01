package ooga.model.rules;

import ooga.model.cards.CardInterface;

public class SameColorRule implements RuleInterface {

  @Override
  @Deprecated
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay) {
    return cardToMatch.getMyColor().equals(cardToPlay.getMyColor());
  }

  @Override
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay, int draw) {
    if (draw != 0)
      return false;

    return cardToMatch.getMyColor().equals(cardToPlay.getMyColor());
  }
}
