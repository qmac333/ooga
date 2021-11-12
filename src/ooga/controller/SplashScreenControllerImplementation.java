package ooga.controller;

import javafx.stage.Stage;
import ooga.view.GameScreen;
import ooga.view.SplashScreen;
import ooga.view.UnoDisplay;

public class SplashScreenControllerImplementation implements SplashScreenController {

  private Stage stage;
  private SplashScreen splashScreen;
  private UnoDisplay gameScreen;

  private boolean gameStarted;

  /**
   * initializes data structures for the splash screen controller
   * @param stage the window for the application
   */
  public SplashScreenControllerImplementation(Stage stage){
    this.stage = stage;
    this.splashScreen = new SplashScreen(this);
    gameStarted = false;
  }

  /**
   * Starts up the application, creates a splash screen.
   */
  public void showScreen() {
    stage.setScene(splashScreen.setScene());
    stage.show();
  }

  @Override
  public void setupProgram(String filepath) {

  }

  @Override
  public void playButtonHandler() {
    if (!gameStarted) {


      gameStarted = true;
    }

  }



}
