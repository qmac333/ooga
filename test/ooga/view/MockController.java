package ooga.view;

import ooga.controller.UnoDisplayController;
import ooga.model.gameState.GameStateViewInterface;

public class MockController implements UnoDisplayController {

  private MockGameViewInterface mock;

  public MockController() {
    mock = new MockGameViewInterface();

  }

  @Override
  public void saveCurrentFile() {

  }

  @Override
  public void backButtonHandler() {

  }

  @Override
  public void playUserCard(int index) {

  }

  @Override
  public GameStateViewInterface getGameState() {
    return mock;
  }

  public void addPlayer() {
    mock.addPlayer();
  }

}
