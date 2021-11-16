package ooga.model.cards;

import ooga.model.gameState.GameState;
import ooga.model.player.Player;

public abstract class Card implements CardInterface {

  private Player owner;
  private GameState game;
  private int myNum;
  private String myColor;
  private String myType;


  public Card(GameState g, String color, String type, int num) {
    game = g;
    myColor = color;
    myType = type;
    myNum = num;
  }

  @Override
  public abstract void executeAction();

  @Override
  public void setPlayer(Player p) {
    owner = p;
    p.addCard(this);
  }

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

  protected GameState getGame() {
    return game;
  }

  protected void setCardColor(String color) {
    myColor = color;
  }
}
