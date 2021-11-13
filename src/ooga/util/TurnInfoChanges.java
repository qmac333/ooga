package ooga.util;

/**
 * A type used as a view parameter to signal what aspects of the model have changed.
 */

public enum TurnInfoChanges {
  NUM_PLAYERS, // players have left or joined the game
  NUM_CARDS, // the number of cards in a player's hand has changed
  DIRECTION, // the direction of gameplay has changed
  CURRENT_PLAYER // the current player has changed
}
