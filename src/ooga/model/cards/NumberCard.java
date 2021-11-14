package ooga.model.cards;

import ooga.model.gameState.GameState;

public class NumberCard extends Card{

  public NumberCard(GameState g, String color, String type) {
    super(g, color, type);
  }

  @Override
  public void executeAction(){
    getGame().specifyPlayingConstraints(this.getNum(), this.getColor());
    super.getGame().discardCard(this);
  }
  @Override
  public String getType() {
    return null;
  }
}
