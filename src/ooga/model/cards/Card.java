package ooga.model.cards;

import ooga.model.gameState.GameState;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.Player;

public abstract class Card implements CardInterface {

  private int myNum;
  private String myColor;
  private String myType;


  public Card(String color, String type, int num) {
    myColor = color;
    myType = type;
    myNum = num;
  }

  @Override
  public abstract void executeAction(GameStatePlayerInterface game);

  @Override
  public int getNum() {
    return myNum;
  }

  @Override
  public String getMyColor() {
    return myColor;
  }

  @Override
  public String getType() {
    return myType;
  }

  protected void setCardColor(String color) {
    myColor = color;
  }
}
