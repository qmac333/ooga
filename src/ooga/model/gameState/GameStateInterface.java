package ooga.model.gameState;

import java.util.List;
import java.util.Map;
import ooga.model.Player;
import ooga.model.cards.Card;

/**
 * Interface implemented by the GameState Class. Provides an API to be used by the Controller for
 * accessing data in the model.
 */
public interface GameStateInterface {

  /**
   * makes the currentPlayer member play its turn
   */
  public void playTurn();

  /**
   * sets the lastCardThown member in the GameState
   */
  public void discardCard(Card c);

  /**
   * gets the tpye of the lastCardThrown
   */
  public String getLastCardThrownType();

  /**
   * reverses the order of play
   */
  public void reverseGamePlay();

  /**
   * called by the Skip Action card to skip the next player
   */
  public void skipNextPlayer();

  /**
   * adds a player to the game
   */
  public void addPlayer(Player p);

  /**
   * returns the player index whose turn it is to play
   *
   * @return index of the current player
   */
  public int getCurrentPlayer();


  /**
   * allows class calling this method to set the nextPlayerDrawTwo member to true
   */
  public void addDraw(int drawAmount);


  /**
   * returns a map of card types to the different Colors and Types of cards that exist in a players
   * hand with that number
   *
   * @return a Map of Integers to Lists of Strings
   */
  public Map<Integer, List<List<String>>> getCurrentPlayerCards();

  /**
   * Gets the next card from the deck
   *
   * @return card at the top of the deck
   */
  public Card getNextCard();
}
