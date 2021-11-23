package ooga.model.deck;

import ooga.model.cards.Card;

import java.util.*;

public class CardPile implements CardPileInterface, CardPileViewInterface {

    private Stack<Card> pile;

    public CardPile(){
        pile = new Stack<Card>();
    }

    @Override
    public void placeOnTop(Card c) {
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
        return pile.peek();
    }

    @Override
    public Card popTopCard() {
        try{
            return pile.pop();
        }
        catch(EmptyStackException e){
            return null;
        }
    }

    @Override
    public void copyOver(CardPileInterface other) {
        Collection<Card> intermediary = new ArrayList<Card>();
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
