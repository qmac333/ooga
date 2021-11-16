package ooga.model.cards;

import ooga.model.gameState.GameState;

public class SkipCard extends Card {

  public SkipCard(GameState g, String color) {
    super(g, color, "Skip", -1);
  }

  @Override
  public void executeAction() {
    GameState myGame = super.getGame();
    myGame.skipNextPlayer();
    myGame.discardCard(this);
  }
}
