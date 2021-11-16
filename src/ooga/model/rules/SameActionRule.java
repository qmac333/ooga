package ooga.model.rules;

import ooga.model.cards.Card;

public class SameActionRule implements RuleInterface {

  @Override
  public boolean canPlay(Card cardToMatch, Card cardToPlay) {
    return cardToMatch.getType().equals(cardToPlay.getType()) && !cardToMatch.getType()
        .equals("Number");
  }
}
