package ooga.model.drawRule.blaster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ooga.model.cards.CardInterface;

public class Blaster implements BlasterInterface {
  List<CardInterface> myCards;
  private double myProbability;

  public Blaster(double probability){
    myCards = new ArrayList<>();
    myProbability = probability;
  }

  @Override
  public Collection<CardInterface> insert(Collection<CardInterface> card) {
    myCards.addAll(card);
    List<CardInterface> blasted;
    if (Math.random() < myProbability){
      blasted = myCards;
      myCards = new ArrayList<>();
    } else{
      blasted = new ArrayList<>();
    }
    return blasted;
  }

  @Override
  public void setProbabilityOfBlast(double probability) {
    myProbability = probability;
  }
}
