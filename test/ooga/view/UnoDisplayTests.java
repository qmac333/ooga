package ooga.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ooga.controller.UnoController;
import ooga.view.gamescreens.LanguageScreen;
import ooga.view.gamescreens.SplashScreen;
import ooga.view.gamescreens.BasicUnoDisplay;
import ooga.view.subdisplays.CardDisplay;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnoDisplayTests extends DukeApplicationTest {

  @Override
  public void start(Stage stage) {
    UnoController controller = new UnoController(stage);
    controller.setLanguage("English");
    controller.createSplashScreen("English");
    controller.loadFile("data/configuration_files/Test Files/validNewFile1.json");
    Button playButton = lookup("#" + SplashScreen.PLAY_CSS_ID).query();
    clickOn(playButton);
  }

  @Test
  public void testBack() {
    Button backButton = lookup("#" + BasicUnoDisplay.BACK_BUTTON_CSS).query();
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
    ImageView themeImage = lookup("#" + BasicUnoDisplay.THEME_IMAGE_CSS).query();
    try {
      Image baseImage = new Image(new FileInputStream("data/images/logos/Basic.png"));
      assertEquals(baseImage.getWidth(), themeImage.getImage().getWidth());
      assertEquals(baseImage.getHeight(), themeImage.getImage().getHeight());
    } catch (FileNotFoundException e) {
      Assertions.fail();
    }

  }



}
