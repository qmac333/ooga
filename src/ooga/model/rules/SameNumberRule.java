package ooga.model.rules;

import ooga.model.cards.CardInterface;

public class SameNumberRule implements RuleInterface {

  @Override
  @Deprecated
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay) {
    if (cardToMatch.getType().equals("Number") && cardToPlay.getType().equals("Number")) {
      return cardToMatch.getNum() == cardToPlay.getNum();
    }
    return false;
  }

  @Override
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay, int draw) {
    if (draw != 0)
      return false;

    if (cardToMatch.getType().equals("Number") && cardToPlay.getType().equals("Number")) {
      return cardToMatch.getNum() == cardToPlay.getNum();
    }
    return false;
  }
}
