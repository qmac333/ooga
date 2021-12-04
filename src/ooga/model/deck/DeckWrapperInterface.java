package ooga.model.deck;

import ooga.model.cards.CardInterface;


/**
 * API for interacting with the DeckWrapper class in the GameState.  Primarily meant to encapsulate
 * boht the deck and discard pile.
 */
public interface DeckWrapperInterface {

    /**
     * Method meant for GameState to draw a card for a player.
     * @return the card on the top of the deck
     */
    public CardInterface draw();


    /**
     * Places card in the discard pile.
     * @param card
     */
    public void discard(CardInterface card);


    /**
     * returns the deck of cards
     * @return
     */
    public CardPileInterface getDeck();


    /**
     * returns the discard pile
     * @return
     */
    public CardPileInterface getDiscardPile();


    /**
     * returns the last card thrown
     * @return
     */
    public CardInterface getLastCard();

    /**
     * returns the top card of the deck
     * @return
     */
    public CardInterface getTopCard();


    /**
     * allows class calling to peek the top of the discard pile
     * @return
     */
    public CardInterface peekTopDiscard();

}


