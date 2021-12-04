package ooga.model.deck;

import ooga.model.cards.CardInterface;

public class DeckWrapper implements DeckWrapperInterface{

    private UnoDeck myDeck;
    private CardPile myDiscardPile;

    public DeckWrapper(UnoDeck deck, CardPile discard){
        myDeck = deck;
        myDiscardPile = discard;
    }

    @Override
    public CardInterface draw() {
        CardInterface newCard = myDeck.popTopCard();
        if(myDeck.getNumCards() == 0){
            myDiscardPile.copyOver(myDeck);
        }
        return newCard;
    }

    @Override
    public void discard(CardInterface card) {
        myDiscardPile.placeOnTop(card);
    }
}
