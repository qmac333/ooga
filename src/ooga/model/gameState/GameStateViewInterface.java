package ooga.model.gameState;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Supplier;
import ooga.model.cards.ViewCardInterface;
import ooga.model.deck.CardPileViewInterface;

/**
 * An interface that allows the view to interact with the game state, but in a read-only way.
 */
public interface GameStateViewInterface {

  public static final int CLOCKWISE = 0;
  public static final int COUNTERCLOCKWISE = 1;

  /**
   * @return a unmodifiable list of all player names
   */
  List<String> getPlayerNames();

  /**
   * @return an unmodifiable list of card counts in each player's hand
   */
  List<Integer> getCardCounts();

  /**
   * return an integer corresponding to the direction of gameplay (CLOCKWISE vs COUNTERCLOCKWISE)
   */
  public int getGameplayDirection();

  /**
   * returns the player index whose turn it is to play
   *
   * @return index of the current player
   */
  public int getCurrentPlayer();

  /**
   * Returns a list of the player's cards to display
   * @return a list of cards in the player's hand
   */
  public List<ViewCardInterface> getCurrentPlayerCards();

  /**
   * Gets read-only access to the deck.
   * @return an interface that provides access to the deck
   */
  public CardPileViewInterface getDeck();

  /**
   * Gets read-only access to attributesof the discard pile.
   * @return an interface that provides access to the discard pile.
   */
  public CardPileViewInterface getDiscardPile();


  /**
   * Plays a turn of the game.
   */
  public void playTurn();

  void createPlayers(Supplier<Integer> supplier) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
}
