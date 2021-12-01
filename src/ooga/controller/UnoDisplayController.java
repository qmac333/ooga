package ooga.controller;

import ooga.model.gameState.GameStateViewInterface;

import java.io.IOException;

/**
 * Interface used to serve as controller for UnoDisplay class.
 */
public interface UnoDisplayController {

  void saveCurrentFile(String filename) throws IOException;

  void backButtonHandler();

  GameStateViewInterface getGameState();

}
