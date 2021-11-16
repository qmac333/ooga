package ooga.model.rules;

import ooga.model.cards.Card;

public class SameNumberRule implements RuleInterface {

  @Override
  public boolean canPlay(Card cardToMatch, Card cardToPlay) {
    if (cardToMatch.getType().equals("Number") && cardToPlay.getType().equals("Number")) {
      return cardToMatch.getNum() == cardToPlay.getNum();
    }
    return false;
  }
}
