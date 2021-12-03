package ooga.model.hand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import ooga.model.cards.CardInterface;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.PlayerInterface;
import org.jetbrains.annotations.NotNull;

public class Hand implements Iterable<CardInterface>, HandInterface {

  private final List<CardInterface> myCards;

  public Hand() {
    myCards = new ArrayList<>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void add(Collection<CardInterface> card) {
    myCards.addAll(card);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public void play(int indexOfCard, GameStatePlayerInterface game)
      throws InvalidCardSelectionException {
    if (indexOfCard >= myCards.size()) {
      throw new InvalidCardSelectionException(
          String.format("Input index: %d is too large", indexOfCard));
    }
    myCards.get(indexOfCard).executeAction(game);
    game.discardCard(myCards.get(indexOfCard));
    myCards.remove(indexOfCard);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void play(int indexOfCard, GameStatePlayerInterface game, PlayerInterface player)
      throws InvalidCardSelectionException {
    if (indexOfCard >= myCards.size()) {
      throw new InvalidCardSelectionException(
          String.format("Input index: %d is too large", indexOfCard));
    }
    myCards.get(indexOfCard).executeAction(player);
    game.discardCard(myCards.get(indexOfCard));
    myCards.remove(indexOfCard);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void flip() {
    for (CardInterface card : myCards) {
      card.flip();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int size() {
    return myCards.size();
  }

  /**
   * {@inheritDoc}
   */
  @NotNull
  @Override
  public Iterator<CardInterface> iterator() {
    return new HandIterator();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<CardInterface> removeColor(String color){
    List<CardInterface> removed = new ArrayList<>();
    for (CardInterface card : myCards){
      if (card.getMyColor().equals(color)){
        removed.add(card);
      }
    }
    myCards.removeAll(removed);
    return removed;
  }

  // Class used to allow us to iterate through the hand
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
