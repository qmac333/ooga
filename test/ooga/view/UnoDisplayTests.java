package ooga.view;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import ooga.controller.UnoController;
import ooga.view.maindisplay.UnoDisplay;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnoDisplayTests extends DukeApplicationTest {

  @Override
  public void start(Stage stage) {
    CardDisplay.initializeCards();
    UnoController controller = new UnoController(stage);
    controller.setLanguage("English");
    controller.createSplashScreen("English");
    controller.loadFile("data/configuration_files/Test Files/validNewFile1.json");
    Button playButton = lookup("#" + SplashScreen.PLAY_CSS_ID).query();
    clickOn(playButton);
  }

  @Test
  public void testBack() {
    Button backButton = lookup("#" + UnoDisplay.BACK_BUTTON_CSS).query();
    clickOn(backButton);

    try {
      Node languagePicker = lookup("#" + LanguageScreen.LANGUAGE_PICKER_CSS).query();
    }
    catch (Exception e) {
      Assertions.fail();
    }
  }

  @Test
  public void testThemeImage() {
    // check that the image is on the screen
    ImageView themeImage = lookup("#" + UnoDisplay.THEME_IMAGE_CSS).query();
  }



}
