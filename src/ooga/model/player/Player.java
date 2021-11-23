package ooga.model.player;

import java.util.ArrayList;
import java.util.List;
import ooga.model.cards.CardInterface;
import ooga.model.cards.ViewCardInterface;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.hand.Hand;

public abstract class Player implements PlayerInterface {

  private Hand myHand;
  private String myName;
  private GameStatePlayerInterface myGame;

  public Player(String name, GameStatePlayerInterface game) {
    myName = name;
    myGame = game;
    myHand = new Hand();
  }

  @Override
  public abstract void playCard();

  @Override
  public void addCards(List<CardInterface> cards) {
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

  protected Hand getMyHand() {
    return myHand;
  }

  protected GameStatePlayerInterface getMyGame() {
    return myGame;
  }
}
