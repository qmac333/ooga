package ooga.model.deck;

import ooga.model.cards.Card;
import ooga.model.deck.CardPileInterface;

import java.util.*;

public abstract class CardPile implements CardPileInterface {

    private Stack<Card> pile;

    public CardPile(){
        pile = new Stack<Card>();
    }

    @Override
    public void pushCard(Card c) {
        pile.push(c);
    }

    @Override
    public Card lastCardPushed() {
        return pile.peek();
    }

    @Override
    public Card popLastCard() {
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
        for(Card c : intermediary){
            other.pushCard(c);
        }
    }
}
