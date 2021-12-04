package ooga.controller;

import ooga.model.gameState.GameStateViewInterface;

/**
 * Interface used to serve as controller for UnoDisplay class.
 */
public interface UnoDisplayController {

  boolean saveCurrentFile(String filename);

  void backButtonHandler();

  GameStateViewInterface getGameState();

  String getGameVersion();

  String getMod();

}
