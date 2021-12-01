package ooga.model.rules;

import ooga.model.cards.CardInterface;

public class SameActionRule implements RuleInterface {

  @Override
  @Deprecated
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay) {
    return cardToMatch.getType().equals(cardToPlay.getType()) && !cardToMatch.getType()
        .equals("Number");
  }

  @Override
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay, int draw) {
    if (draw != 0)
      return false;

    return cardToMatch.getType().equals(cardToPlay.getType()) && !cardToMatch.getType()
        .equals("Number");
  }
}
