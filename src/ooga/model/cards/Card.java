package ooga.model.cards;

import java.util.function.Supplier;
import ooga.model.gameState.GameState;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.Player;
import ooga.model.player.PlayerInterface;

public abstract class Card implements CardInterface, ViewCardInterface {

  private int myNum;
  private String myColor;
  private String myType;


  public Card(String color, String type, int num) {
    myColor = color;
    myType = type;
    myNum = num;
  }

  @Override
  @Deprecated
  public abstract void executeAction(GameStatePlayerInterface game);

  @Override
  public abstract void executeAction(PlayerInterface player);

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
