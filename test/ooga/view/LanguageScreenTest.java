package ooga.view;

import java.util.ResourceBundle;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.controller.UnoController;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class LanguageScreenTest extends DukeApplicationTest {

  private UnoController controller;
  private ResourceBundle cssIdResources;
  private ResourceBundle textResources = ResourceBundle.getBundle("ooga.resources.English");

  @Override
  public void start(Stage stage) {
    controller = new UnoController(stage);
    controller.start();
    cssIdResources = ResourceBundle.getBundle("ooga.resources.CSSId");
  }

  @Test
  public void testLanguagePick() {
    ChoiceBox<String> picker = lookup("#" + cssIdResources.getString("LanguagePicker")).query();
    clickOn(picker);
    select(picker, "English");
    Text titleText = lookup("#" + cssIdResources.getString("SplashScreenTitleText")).query();
    assertEquals(textResources.getString("Title"), titleText.getText());


  }


}
