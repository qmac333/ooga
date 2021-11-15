package ooga.model;

import java.util.Collection;
import java.util.List;
import ooga.model.cards.Card;

public class Player implements PlayerInterface {

  private List<Card> myHand;
  private String myName;

  public Player(String name) {
    myName = name;
  }

  @Override
  public void playCard() {
//    int index = consumer.getIndextoPlay();
//    myHand.get(index).executeAction();
  }

  @Override
  public void addCard(Card card) {
    myHand.add(card);
  }

  @Override
  public Collection<Card> getHand() {
    return null;
  }

  @Override
  public String getName() {
    return myName;
  }
}
