package ooga.model.player;

public interface ViewPlayerInterface {

  /**
   * @return Name of the player
   */
  String getName();

  /**
   * @return The number of cards in the player's hand
   */
  int getHandSize();

  /**
   * @return The number of points the player has
   */
  int getPoints();
}
