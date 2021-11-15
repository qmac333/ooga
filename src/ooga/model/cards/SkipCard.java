package ooga.model.cards;

import ooga.model.gameState.GameState;

public class SkipCard extends ActionCard {

  public SkipCard(GameState g, String color, String type) {
    super(g, color, type);
  }

  @Override
  public void executeAction() {
    GameState myGame = super.getGame();
    myGame.skipNextPlayer();
    super.getGame().discardCard(this);
  }
}
