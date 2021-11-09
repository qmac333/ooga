package ooga.model;


/**
 * interface implemented by all Cards in a game. changes the GameState
 * and/or player that holds the card
 */
public interface CardInterface {

    /**
     * Executes whatever the card's action is on the GameState and/or
     * player which holds the card. This can include identifying cards that
     * can be played in the case of a Number Card, or changing the GameState in
     * place of an Action Card.
     */
    public void executeAction();


    /**
     * Set's a card's owner to a certain player, i.e. places this card in a player's hand.
     * @param p
     */
    public void setPlayer(Player p);
}
