package ooga.model.cards;

import java.util.function.Supplier;
import ooga.model.gameState.GameState;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.player.Player;

public abstract class Card implements CardInterface, ViewCardInterface {

  private int myNum;
  private String myColor;
  private String myType;
  private final Supplier<String> mySupplier;


  public Card(String color, String type, int num, Supplier<String> supplier) {
    myColor = color;
    myType = type;
    myNum = num;
    mySupplier = supplier;
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

  protected Supplier<String> getSupplier(){
    return mySupplier;
  }
}
