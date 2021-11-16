package ooga.model.player;

import ooga.model.gameState.GameStatePlayerInterface;

public class HumanPlayer extends Player {

  public HumanPlayer(String name, GameStatePlayerInterface game) {
    super(name, game);
  }

  @Override
  public void playCard() {
    // Ask the view to get us a card index
  }
}
