package ooga.view;

import ooga.controller.UnoDisplayController;
import ooga.model.gameState.GameStateViewInterface;

public class MockController implements UnoDisplayController {

  private MockGameViewInterface mock;

  public MockController() {
    mock = new MockGameViewInterface();

  }

  @Override
  public void saveCurrentFile(String filename) {

  }

  @Override
  public void backButtonHandler() {

  }

  @Override
  public GameStateViewInterface getGameState() {
    return mock;
  }

  public void addPlayer() {
    mock.addPlayer();
  }

}
