package ooga.model.cards;

/**
 * Interface used to allow the view to access cards from the model.
 */
public interface ViewCardInterface {

  /**
   * @return The type of the Card
   */
  String getType();

  /**
   * @return The number of the card
   */
  int getNum();

  /**
   * @return The color of the card
   */
  String getMyColor();
}
