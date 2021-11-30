package ooga.model.deck;

import ooga.model.cards.Card;

import java.util.*;
import ooga.model.cards.CardInterface;
import ooga.model.cards.ViewCardInterface;

public class CardPile implements CardPileInterface, CardPileViewInterface {

    private Stack<CardInterface> pile;

    public CardPile(){
        pile = new Stack<CardInterface>();
    }

    @Override
    public void placeOnTop(CardInterface c) {
        pile.push(c);
    }

    @Override
    public int getNumCards() {
        return pile.size();
    }

    public void placeOnTop(Collection<Card> cards){
        addFromCollection(cards);
    }

    @Override
    public Card lastCardPushed() {
        return (Card) pile.peek();
    }

    @Override
    public CardInterface popTopCard() {
        try{
            return pile.pop();
        }
        catch(EmptyStackException e){
            return null;
        }
    }

    @Override
    public void copyOver(CardPileInterface other) {
        Collection<CardInterface> intermediary = new ArrayList<CardInterface>();
        intermediary.addAll(pile);
        pile.clear();

        Collections.shuffle((List<?>) intermediary);

    }

    private void addFromCollection(Collection<Card> cardGroup){
        for(Card c : cardGroup){
            this.placeOnTop(c);
        }
    }
}
