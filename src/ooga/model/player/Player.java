package ooga.model.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ooga.model.cards.Card;
import ooga.model.gameState.GameState;
import ooga.model.gameState.GameStatePlayerInterface;

public abstract class Player implements PlayerInterface {

  private List<Card> myHand;
  private String myName;
  private GameStatePlayerInterface myGame;

  public Player(String name, GameStatePlayerInterface game) {
    myName = name;
    myGame = game;
    myHand = new ArrayList<>();
  }

  @Override
  public abstract void playCard();

  @Override
  public void addCards(List<Card> cards) {
    myHand.addAll(cards);
  }

  @Override
  public Collection<Card> getHand() {
    return null;
  }

  @Override
  public String getName() {
    return myName;
  }

  protected List<Card> getMyHand(){
    return myHand;
  }

  protected GameStatePlayerInterface getMyGame(){
    return myGame;
  }
}
