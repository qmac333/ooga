package ooga.view;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import ooga.controller.UnoController;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class UnoDisplayTests extends DukeApplicationTest {

  @Override
  public void start(Stage stage) {
    UnoController controller = new UnoController(stage);
    controller.start();
    controller.loadNewHandler("data/configurationfiles/example1.json");
    Button playButton = lookup("#" + SplashScreen.PLAY_CSS_ID).query();
    clickOn(playButton);
  }

  @Test
  public void testBack() {
    pause(500);
    Button backButton = lookup("#" + UnoDisplay.BACK_BUTTON_CSS).query();
    clickOn(backButton);

    // assert that the playButton is on the screen
    Button playButton = lookup("#" + SplashScreen.PLAY_CSS_ID).query();

  }

  private void pause(double millis) {
    long init = System.currentTimeMillis();
    while (System.currentTimeMillis() < init + millis) {
      // spin
    }
  }


}
