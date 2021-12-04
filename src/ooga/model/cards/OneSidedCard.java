package ooga.model.cards;

import java.util.function.Supplier;
import ooga.model.gameState.GameState;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.Player;
import ooga.model.player.PlayerInterface;

public abstract class OneSidedCard implements CardInterface, ViewCardInterface {

  private int myNum;
  private String myColor;
  private String myType;


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
  public abstract void executeAction(PlayerInterface player);

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

  // Sets the color of the card to whatever is specified
  protected void setCardColor(String color) {
    myColor = color;
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
}
