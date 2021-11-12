package ooga.model;

import java.util.Collection;
import java.util.List;

public class Player implements PlayerInterface{

    private List<Card> myHand;

    public Player(){}
    

    @Override
    public void playCard(int index) {
        myHand.get(index).executeAction();
    }

    @Override
    public void addCard(Card card) {
        myHand.add(card);
    }

    @Override
    public Collection<Card> getHand() {
        return null;
    }
}
