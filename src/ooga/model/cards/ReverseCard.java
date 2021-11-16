package ooga.model.cards;

import ooga.model.gameState.GameState;

public class ReverseCard extends Card {

  public ReverseCard(GameState g, String color) {
    super(g, color, "Reverse", -1);
  }

  @Override
  public void executeAction() {
    GameState myGame = super.getGame();
    myGame.reverseGamePlay();
    myGame.discardCard(this);
  }
}
