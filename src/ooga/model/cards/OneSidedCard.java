package ooga.model.cards;

import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.player.PlayerCardInterface;

public abstract class OneSidedCard implements CardInterface, ViewCardInterface {

  private final int myNum;
  private String myColor;
  private final String myType;


  public OneSidedCard(String color, String type, int num) {
    myColor = color;
    myType = type;
    myNum = num;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public abstract void executeAction(GameStatePlayerInterface game);

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract void executeAction(PlayerCardInterface player);

  /**
   * {@inheritDoc}
   */
  @Override
  public int getNum() {
    return myNum;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMyColor() {
    return myColor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getType() {
    return myType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void flip(){
    // Do Nothing
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object other){
    CardInterface otherCard = (CardInterface) other;
    boolean condition1 = this.getMyColor().equals(otherCard.getMyColor());
    boolean condition2 = (this.getNum() == otherCard.getNum());
    boolean condition3 = this.getType().equals(otherCard.getType());
    return condition1 && condition2 && condition3;
  }

  @Override
  public void setColor(String color) {
    myColor = color;
  }

  // Sets the color of the card to whatever is specified
  protected void setCardColor(String color) {
    myColor = color;
  }
}
