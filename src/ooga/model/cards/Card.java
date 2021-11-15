package ooga.model.cards;

import ooga.model.gameState.GameState;
import ooga.model.Player;

public abstract class Card implements CardInterface {

  private Player owner;
  private GameState game;
  private int num;
  private String color;
  private boolean canPlay;


  public Card(GameState g, String color, String type) {
    game = g;
    this.color = color;
    canPlay = true;
  }

  @Override
  public void executeAction() {
  }

  @Override
  public void setPlayer(Player p) {
    owner = p;
    p.addCard(this);
  }

  @Override
  public int getNum(){
    return num;
  }

  @Override
  public String getColor(){
    return color;
  }

  protected GameState getGame() {
    return game;
  }

  protected void setCardColor(String color) {
    this.color = color;
  }
}
