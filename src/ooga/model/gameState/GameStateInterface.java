package ooga.model.gameState;

import ooga.model.cards.CardInterface;
import ooga.model.player.Player;

/**
 * Interface implemented by the GameState Class. Provides an API to be used by the Controller for
 * accessing data in the model.
 */
public interface GameStateInterface {

  /**
   * makes the currentPlayer member play its turn
   */
  void playTurn();

  /**
   * sets the lastCardThown member in the GameState
   */
  void discardCard(CardInterface c);

  /**
   * gets the tpye of the lastCardThrown
   */
  String getLastCardThrownType();

  /**
   * reverses the order of play
   */
  void reverseGamePlay();

  /**
   * called by the Skip Action card to skip the next player
   */
  void skipNextPlayer();

  /**
   * adds a player to the game
   */
  void addPlayer(Player p);

  /**
   * returns the player index whose turn it is to play
   *
   * @return index of the current player
   */
  int getCurrentPlayer();


  /**
   * allows class calling this method to set the nextPlayerDrawTwo member to true
   */
  void addDraw(int drawAmount);

  /**
   * Gets the next card from the deck
   *
   * @return card at the top of the deck
   */
  CardInterface getNextCard();

  /**
   * returns the order of play as an integer
   * @return order member
   */
  int getOrder();
}
