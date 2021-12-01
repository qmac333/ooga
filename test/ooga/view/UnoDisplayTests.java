package ooga.view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ooga.controller.UnoController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class UnoDisplayTests extends DukeApplicationTest {

  @Override
  public void start(Stage stage) {
    CardDisplay.initializeCards();
    UnoController controller = new UnoController(stage);
    controller.createSplashScreen("English");
    controller.loadNewFile("data/configurationfiles/validNewFile1.json");
    Button playButton = lookup("#" + SplashScreen.PLAY_CSS_ID).query();
    clickOn(playButton);
  }

  @Test
  public void testBack() {
    pause(500);
    Button backButton = lookup("#" + UnoDisplay.BACK_BUTTON_CSS).query();
    clickOn(backButton);

    try {
      Node languagePicker = lookup("#" + LanguageScreen.LANGUAGE_PICKER_CSS).query();
    }
    catch (Exception e) {
      Assertions.fail();
    }




  }

  private void pause(double millis) {
    long init = System.currentTimeMillis();
    while (System.currentTimeMillis() < init + millis) {
      // spin
    }
  }


}
