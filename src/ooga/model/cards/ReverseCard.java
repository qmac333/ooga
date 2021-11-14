package ooga.model.cards;

import ooga.model.gameState.GameState;

public class ReverseCard extends ActionCard {

  public ReverseCard(GameState g, String color, String type) {
    super(g, color, type);
  }

  @Override
  public void executeAction() {
    GameState myGame = super.getGame();
    myGame.reverseGamePlay();
    super.getGame().discardCard(this);
  }
}
