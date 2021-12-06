package ooga.model.drawRule.blaster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import ooga.model.cards.CardInterface;
import ooga.model.cards.ViewCardInterface;

public class Blaster implements BlasterInterface {
  List<CardInterface> myCards;
  private double myProbability;

  public Blaster(double probability){
    myCards = new ArrayList<>();
    myProbability = probability;
  }

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
  @Override
  public void setProbabilityOfBlast(double probability) {
    myProbability = probability;
  }

  @Override
  public Collection<ViewCardInterface> getCards() {
    return myCards.stream().map((CardInterface c)-> (ViewCardInterface) c).collect(Collectors.toList());
  }
}
