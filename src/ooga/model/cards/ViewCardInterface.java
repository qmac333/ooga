package ooga.model.cards;

/**
 * Interface used to allow the view to access cards from the model.
 */
public interface ViewCardInterface {

  /**
   * returns a card's type member
   *
   * @return
   */
  String getType();

  /**
   * returns a cards number
   *
   * @return
   */
  int getNum();

  /**
   * returns a card's color
   *
   * @return
   */
  String getMyColor();
}
