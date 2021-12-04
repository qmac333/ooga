package ooga.model.player;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import ooga.model.cards.CardInterface;
import ooga.model.cards.ViewCardInterface;
import ooga.model.deck.CardPile;
import ooga.model.deck.DeckWrapperInterface;
import ooga.model.hand.Hand;

public interface PlayerGroupInterface {

  /**
   * Returns a list of all Players sectioned into an interface that only lets the view do certain
   * things
   *
   * @return this list
   */
  List<ViewPlayerInterface> getViewPlayers();

  /**
   * @return The indexes of cards that can be played by the current Player
   */
  Collection<Integer> getCurrentPlayerValidIndexes();

  /**
   * Causes the player who played this card to be up again
   */
  void skipEveryone();

  /**
   * @return View Objects for the current Player
   */
  List<ViewCardInterface> getCurrentPlayerCards();

  /**
   * Causes the next player in line to be skipped
   */
  void skipNextPlayer();

  /**
   * Reverses the order of the game
   */
  void reverseOrder();

  /**
   * Causes everyone to flip their hands
   */
  void flipGame();

  /**
   * Adds the correct draw to be adhered to in the GameState
   *
   * @param drawAmount Drawing code to set
   */
  void enforceDraw(int drawAmount);

  /**
   * Discards the card for a player
   *
   * @param card Card to discard
   */
  void discardCard(CardInterface card);

  /**
   * Sets the suppliers that will be used for the user input
   *
   * @param integerSupplier Supplier to get the Index of card to Play
   * @param stringSupplier  Supplier to get Color to pick
   */
  void setSuppliers(Supplier<Integer> integerSupplier, Supplier<String> stringSupplier);

  /**
   * Causes current player to play a turn
   */
  void playTurn();

  /**
   * Returns whether a card can be played
   *
   * @param card Card to play
   * @return Whether it's a valid move
   */
  boolean canPlayCard(CardInterface card);

  /**
   * Adds player to the game
   *
   * @param player Player to add
   */
  void addPlayer(PlayerInterface player);

  /**
   * @return Index of Player whose turn it is
   * <p>
   * For saving
   */
  int getCurrentPlayer();

  /**
   * @return Map of Player Name to their Type
   * <p>
   * For saving
   */
  Map<String, String> getPlayerMap();

  /**
   * @return A list of all the hands
   * <p>
   * For saving purposes
   */
  List<Hand> getHands();

  /**
   * @return whether the current player is a human
   */
  boolean userPicksCard();

  /**
   * Deals all the cards to the players
   *
   * @param deck           Deck to get cards from
   * @param cardsPerPlayer Cards each player gets
   */
  void dealCards(DeckWrapperInterface deck, int cardsPerPlayer);

  /**
   * Sets which order the game is moving
   *
   * @param order Order of the game
   *              <p>
   *              Needed for loading existing game
   */
  void setOrder(int order);

  /**
   * Loads all the hands into the players
   *
   * @param handsToLoad Hands we want to give
   *                    <p>
   *                    Needed for loading a game
   */
  void loadHands(List<Hand> handsToLoad);

  /**
   * @return The order of the game
   * <p>
   * Needed to save Game
   */
  int getMyOrder();

  /**
   * Performs a draw for the player
   *
   * @return The cards returned by the draw
   */
  Collection<CardInterface> noPlayDraw();
}
