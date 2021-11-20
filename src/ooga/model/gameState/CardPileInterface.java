package ooga.model.gameState;


import ooga.model.cards.Card;

/**
 * Interface which describes the functionality of both the Uno card deck and the discard pile.
 * Will include methods for easy access to both structures without giving up the data structure
 * itself.
 */
public interface CardPileInterface {

    /**
     * pushes card to the top of a pile of cards
     */
    public void pushCard(Card c);

    /**
     * allows caller to see the card at the top of a
     * pile of cards
     */
    public Card lastCardPushed();

    /**
     * removes a card from the top of a pile of cards
     */
    public Card popLastCard();

    /**
     * removes all the cards from one CardPile and adds them to another,
     * emptying the first one in the process
     */
    public void copyOver(CardPileInterface other);
}