package ooga.model.rules;

import ooga.model.cards.CardInterface;

public class SameColorRule implements RuleInterface {

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay) {
    return cardToMatch.getMyColor().equals(cardToPlay.getMyColor());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canPlay(CardInterface cardToMatch, CardInterface cardToPlay, int draw) {
    if (draw != 0)
      return false;

    return cardToMatch.getMyColor().equals(cardToPlay.getMyColor()) || cardToMatch.getMyColor().equals("Black");
  }
}
