package ooga.model.deck;


import ooga.model.cards.OneSidedCard;
import ooga.model.cards.CardInterface;

/**
 * Interface which describes the functionality of both the Uno card deck and the discard pile.
 * Will include methods for easy access to both structures without giving up the data structure
 * itself.
 */
public interface CardPileInterface {

    /**
     * pushes card to the top of a pile of cards
     */
    public void placeOnTop(CardInterface c);

    /**
     * gets the number of cards in a pile
     */
    public int getNumCards();


    /**
     * allows caller to see the card at the top of a
     * pile of cards
     */
    public CardInterface lastCardPushed();

    /**
     * removes a card from the top of a pile of cards
     */
    public CardInterface popTopCard();

    /**
     * removes all the cards from one CardPile and adds them to other,
     * emptying the first one in the process
     */
    public void copyOver(CardPileInterface other);
}
