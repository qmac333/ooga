package ooga.model.deck;

import ooga.model.cards.OneSidedCard;

import java.util.*;
import ooga.model.cards.CardInterface;

public class CardPile implements CardPileInterface, CardPileViewInterface {

    private Stack<CardInterface> pile;

    public CardPile(){
        pile = new Stack<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void placeOnTop(CardInterface c) {
        pile.push(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumCards() {
        return pile.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void placeOnTop(Collection<CardInterface> cards){
        addFromCollection(cards);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OneSidedCard lastCardPushed() {
        return (OneSidedCard) pile.peek();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CardInterface popTopCard() {
        try{
            return pile.pop();
        }
        catch(EmptyStackException e){
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void copyOver(CardPileInterface other) {
        Collection<CardInterface> intermediary = new ArrayList<CardInterface>();
        intermediary.addAll(pile);

        pile.clear();

        Collections.shuffle((List<?>) intermediary);
        other.placeOnTop(intermediary);
    }


    private void addFromCollection(Collection<CardInterface> cardGroup){
        for(CardInterface c : cardGroup){
            this.placeOnTop(c);
        }
    }

    /**
     * Used by the Save File feature
     * @return the Stack of Cards
     */
    public Stack<CardInterface> getStack(){
        return pile;
    }
}
