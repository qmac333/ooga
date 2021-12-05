package ooga.model.deck;


import ooga.model.cards.OneSidedCard;
import ooga.model.cards.CardInterface;

import java.util.Collection;
import java.util.Stack;

/**
 * Interface which describes the functionality of both the Uno card deck and the discard pile.
 * Will include methods for easy access to both structures without giving up the data structure
 * itself.
 */
public interface CardPileInterface {

    /**
     * pushes card to the top of a pile of cards
     */
    void placeOnTop(CardInterface c);

    /**
     * merges a collection of cards into pile
     * @param c
     */
    void placeOnTop(Collection<CardInterface> c);

    /**
     * gets the number of cards in a pile
     */
    int getNumCards();


    /**
     * allows caller to see the card at the top of a
     * pile of cards
     */
    CardInterface lastCardPushed();

    /**
     * removes a card from the top of a pile of cards
     */
    CardInterface popTopCard();

    /**
     * removes all the cards from one CardPile and adds them to other,
     * emptying the first one in the process
     */
    void copyOver(CardPileInterface other);


    /**
     * sets the pile the a new Stack
     * @param p
     */
    void setPile(Stack<CardInterface> p);
}
