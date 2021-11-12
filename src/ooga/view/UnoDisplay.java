package ooga.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class UnoDisplay {

  /**
   * initializes data structures and saves the given stage to a global variable
   *
   * @param stage the current stage that the display will be representing
   */
  public UnoDisplay(Stage stage) {

  }

  /**
   * creates the Scene for the display, can be used to set the scene for a splash screen
   * which pops up upon loading the game and it can set the scene for the regular game display.
   * this will be called by the controller the switch between scenes
   *
   * @return the current scene that we are displaying
   */
  public Scene setScene() {
    return null;
  }

  /**
   * provides a user-friendly way for errors to be displayed controller needs to call this method
   * when errors are encountered
   *
   * @param e the exception that was thrown from the error
   */
  public void showError(Exception e) {

  }


}
