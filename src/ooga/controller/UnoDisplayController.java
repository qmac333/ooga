package ooga.controller;

import ooga.model.GameState;
import ooga.model.GameStateViewInterface;

/**
 * Interface used to serve as controller for UnoDisplay class.
 */
public interface UnoDisplayController {

  void playUserCard(int index);
  void backButtonHandler();
  GameStateViewInterface getGameState();

}
