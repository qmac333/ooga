package ooga.model.hand;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ooga.model.cards.CardInterface;
import ooga.model.gameState.GameStatePlayerInterface;
import org.jetbrains.annotations.NotNull;

public class Hand implements Iterable<CardInterface>, HandInterface {

  private final List<CardInterface> myCards;

  public Hand() {
    myCards = new ArrayList<>();
  }

  @Override
  public void add(List<CardInterface> card) {
    myCards.addAll(card);
  }

  @Override
  public void play(int indexOfCard, GameStatePlayerInterface game)
      throws InvalidCardSelectionException {
    if (indexOfCard >= myCards.size()) {
      throw new InvalidCardSelectionException(
          String.format("Input index: %d is too large", indexOfCard));
    }
    myCards.get(indexOfCard).executeAction(game);
    myCards.remove(indexOfCard);
  }

  @Override
  public void flip() {
    for (CardInterface card : myCards) {
      card.flip();
    }
  }

  @Override
  public int size() {
    return myCards.size();
  }

  @NotNull
  @Override
  public Iterator<CardInterface> iterator() {
    return new HandIterator();
  }

  private class HandIterator implements Iterator<CardInterface> {

    private int position = 0;

    @Override
    public boolean hasNext() {
      return position < myCards.size();
    }

    @Override
    public CardInterface next() {
      if (hasNext()) {
        return myCards.get(position++);
      }
      return null;
    }
  }
}
