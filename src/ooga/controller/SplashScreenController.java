package ooga.controller;

/**
 * Interface used to serve as the controller for the splash screen view.
 */
public interface SplashScreenController {

  void playButtonHandler();

  void loadExistingHandler(String filepath);

  void loadNewHandler();

  void languageHandler();

}