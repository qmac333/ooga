package ooga.controller;

import ooga.model.gameState.GameStateViewInterface;

/**
 * Interface used to serve as controller for UnoDisplay class.
 */
public interface UnoDisplayController {

  void saveCurrentHandler();

  void backButtonHandler();

  void playUserCard(int index);

  GameStateViewInterface getGameState();

}
