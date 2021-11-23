package ooga.model.rules;

import ooga.model.cards.CardInterface;

public class SameActionRule implements RuleInterface {

  @Override
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay) {
    return cardToMatch.getType().equals(cardToPlay.getType()) && !cardToMatch.getType()
        .equals("Number");
  }
}
