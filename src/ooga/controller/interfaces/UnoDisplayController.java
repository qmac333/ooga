package ooga.controller.interfaces;

import ooga.model.gameState.GameStateViewInterface;

/**
 * Interface used to serve as controller for UnoDisplay class.
 */
public interface UnoDisplayController {

  boolean saveCurrentFile(String filename);

  void returnToSplashScreen();

  GameStateViewInterface getGameState();

  String getGameVersion();
}
