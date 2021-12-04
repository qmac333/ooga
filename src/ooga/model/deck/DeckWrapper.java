package ooga.model.deck;

public class DeckWrapper {

    private UnoDeck myDeck;
    private CardPile myDiscardPile;

    public DeckWrapper(UnoDeck deck, CardPile discard){
        myDeck = deck;
        myDiscardPile = discard;
    }
}
