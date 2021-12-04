package ooga.model.gameState;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import ooga.model.cards.ViewCardInterface;
import ooga.model.deck.CardPileViewInterface;
import ooga.model.player.ViewPlayerInterface;

/**
 * An interface that allows the view to interact with the game state, but in a read-only way.
 */
public interface GameStateViewInterface {

  /**
   * Returns all players in a view friendly way
   *
   * @return The players
   */
  List<ViewPlayerInterface> getPlayers();

  /**
   * return an integer corresponding to the direction of gameplay (CLOCKWISE vs COUNTERCLOCKWISE)
   */
  int getGameplayDirection();

  /**
   * returns the player index whose turn it is to play
   *
   * @return index of the current player
   */
  int getCurrentPlayer();

  /**
   * Returns a list of the player's cards to display
   * @return a list of cards in the player's hand
   */
  List<ViewCardInterface> getCurrentPlayerCards();

  /**
   * Gets read-only access to the deck.
   * @return an interface that provides access to the deck
   */
  CardPileViewInterface getDeck();

  /**
   * Gets read-only access to attributesof the discard pile.
   * @return an interface that provides access to the discard pile.
   */
  CardPileViewInterface getDiscardPile();


  /**
   * Plays a turn of the game.
   */
  void playTurn();

  /**
   * Pass in a handler that can be called by the model to select a card to play for a human player.
   * @param integerSupplier is a function that returns the index of the card to be played in a user's hand
   * @param stringSupplier returns the requested string
   * @throws ClassNotFoundException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  @Deprecated
  void createPlayers(Supplier<Integer> integerSupplier, Supplier<String> stringSupplier) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

  /**
   * Creates a deck of cards.
   * @param map contains function handlers as values that are called if a card in the map is played with the type being a key in the map.
   */
  @Deprecated
  void createDeck(Map<String, Supplier<String>> map);

  /**
   * allows class calling this method to determine whether the end of the game has been reached
   */
  boolean getEndGame();

  /**
   * Allows the model to set a variable if uno is chosen by the user
   * @param called whether uno was called by clicking a button in the display
   */
  void setCalledUno(boolean called);

  /**
   * Creates the players and passes them the correct Integer supplier
   * @param integerSupplier supplier to give them index of card to play
   * @throws ClassNotFoundException for reflection
   * @throws NoSuchMethodException for reflection
   * @throws InvocationTargetException for reflection
   * @throws InstantiationException for reflection
   * @throws IllegalAccessException
   */
  @Deprecated
  void createPlayers(Supplier<Integer> integerSupplier) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

  /**
   * Passes the suppliers to the players
   *
   * @param integerSupplier supplier to get card index
   * @param stringSupplier supplier to get color
   */
  void setSuppliers(Supplier<Integer> integerSupplier, Supplier<String> stringSupplier);

  /**
   * @return true if we need the user to pick a card
   */
  boolean userPicksCard();

  /**
   * @return the indexes of cards that can be played by the user
   */
  Collection<Integer> getValidIndexes();
}
