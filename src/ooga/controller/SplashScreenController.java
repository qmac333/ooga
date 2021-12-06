package ooga.controller;

import java.util.Map;

/**
 * Interface used to serve as the controller for the splash screen view.
 */
public interface SplashScreenController {

  boolean playNewGame();

  boolean setGameParameters(String version, Map<String, String> playerMap, int pointsToWin, boolean stackable);

  boolean loadFile(String filepath);

  void setColorThemeFilepath(String cssFile);

}
