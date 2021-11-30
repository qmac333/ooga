package ooga.model.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import ooga.model.cards.CardInterface;
import ooga.model.cards.ViewCardInterface;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.hand.Hand;

public abstract class Player implements PlayerInterface {

  private Hand myHand;
  private String myName;
  private GameStatePlayerInterface myGame;
  private Supplier<Integer> myIntegerSupplier;

  public Player(String name, GameStatePlayerInterface game, Supplier<Integer> supplier) {
    myName = name;
    myGame = game;
    myHand = new Hand();
    myIntegerSupplier = supplier;
  }

  @Override
  public abstract void playCard();

  @Override
  public void addCards(Collection<CardInterface> cards) {
    myHand.add(cards);
  }

  @Override
  public String getName() {
    return myName;
  }

  @Override
  public int getNumPoints() {
    int sum = 0;
    for (CardInterface c : myHand) {
      sum += c.getNum();
    }
    return sum;
  }

  @Override
  public int getHandSize() {
    return myHand.size();
  }

  @Override
  public List<ViewCardInterface> getViewCards() {
    ArrayList<ViewCardInterface> ret = new ArrayList<>();
    for (CardInterface card : myHand) {
      ret.add((ViewCardInterface) card);
    }
    return ret;
  }

  @Override
  public void flipHand(){
    myHand.flip();
  }

  protected Hand getMyHand() {
    return myHand;
  }

  protected GameStatePlayerInterface getMyGame() {
    return myGame;
  }

  protected Supplier<Integer> getMyIntegerSupplier(){
    return myIntegerSupplier;
  }
}
