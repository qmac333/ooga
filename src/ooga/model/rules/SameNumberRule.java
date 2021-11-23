package ooga.model.rules;

import ooga.model.cards.CardInterface;

public class SameNumberRule implements RuleInterface {

  @Override
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay) {
    if (cardToMatch.getType().equals("Number") && cardToPlay.getType().equals("Number")) {
      return cardToMatch.getNum() == cardToPlay.getNum();
    }
    return false;
  }
}
