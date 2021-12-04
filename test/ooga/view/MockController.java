package ooga.view;

import ooga.controller.UnoDisplayController;
import ooga.model.gameState.GameStateViewInterface;

public class MockController implements UnoDisplayController {

  private MockGameViewInterface mock;

  public MockController() {
    mock = new MockGameViewInterface();

  }

  @Override
  public boolean saveCurrentFile(String filename) {
    return true;
  }

  @Override
  public void toSplashScreen() {

  }

  @Override
  public GameStateViewInterface getGameState() {
    return mock;
  }

  @Override
  public String getGameVersion() {
    return null;
  }

  public void addPlayer() {
    mock.addPlayer();
  }

}
