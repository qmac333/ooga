package ooga.model.deck;

import ooga.model.cards.CardInterface;

public class DeckWrapper implements DeckWrapperInterface{

    private UnoDeck myDeck;
    private CardPile myDiscardPile;

    public DeckWrapper(UnoDeck deck, CardPile discard){
        myDeck = deck;
        myDiscardPile = discard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CardInterface draw() {
        CardInterface newCard = myDeck.popTopCard();
        if(myDeck.getNumCards() == 0){
            myDiscardPile.copyOver(myDeck);
        }
        return newCard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void discard(CardInterface card) {
        myDiscardPile.placeOnTop(card);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CardPileInterface getDeck() {
        return myDeck;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CardPileInterface getDiscardPile() {
        return myDiscardPile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CardInterface getLastCard() {
        return myDiscardPile.lastCardPushed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CardInterface getTopCard() {
        return myDeck.popTopCard();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CardInterface peekTopDiscard() {
        return myDiscardPile.lastCardPushed();
    }
}
