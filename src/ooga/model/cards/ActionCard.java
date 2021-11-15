package ooga.model.cards;

import ooga.model.gameState.GameState;

public abstract class ActionCard extends Card {

  private final String type;

  public ActionCard(GameState g, String color, String type) {
    super(g, color, type);
    this.type = type;
  }

  @Override
  public String getType() {
    return type;
  }
}
