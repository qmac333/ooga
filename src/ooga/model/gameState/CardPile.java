package ooga.model.gameState;

import ooga.model.cards.Card;

import java.util.Collection;

public class CardPile implements CardPileInterface{

    private Collection<Card> pile;

    public CardPile(){};

    @Override
    public void pushCard(Card c) {
        
    }

    @Override
    public Card lastCardPushed() {
        return null;
    }

    @Override
    public Card popLastCard() {
        return null;
    }

    @Override
    public void copyOver(CardPileInterface other) {

    }
}
