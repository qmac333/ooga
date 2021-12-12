package ooga.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.controller.UnoController;
import ooga.model.cards.NumberCard;
import ooga.view.gamescreens.LanguageScreen;
import ooga.view.gamescreens.SplashScreen;
import ooga.view.gamescreens.BasicUnoDisplay;
import ooga.view.subdisplays.CardDisplay;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnoDisplayTests extends DukeApplicationTest {

  private ResourceBundle cssResources;
  private ResourceBundle cssIdResources = ResourceBundle.getBundle("ooga.resources.CSSId");
  private ResourceBundle textResources = ResourceBundle.getBundle("ooga.resources.English");
  private UnoController controller;

  @Override
  public void start(Stage stage) {
    cssResources = ResourceBundle.getBundle("ooga.resources.CSSId");
    controller = new UnoController(stage);
    controller.setLanguage("English");
    controller.createSplashScreen("English");
    controller.loadFile("data/configuration_files/Test Files/validNewFile1.json");
    Button playButton = lookup("#" + cssIdResources.getString("PlayButton")).query();
    clickOn(playButton);
  }

  @Test
  public void testBack() {
    Button backButton = lookup("#" + cssResources.getString("BackButton")).query();
    clickOn(backButton);

    try {
      Node languagePicker = lookup("#" + cssIdResources.getString("LanguagePicker")).query();
    } catch (Exception e) {
      Assertions.fail();
    }
  }

  @Test
  public void testSave() {
    Button saveButton = lookup("#" + cssResources.getString("SaveButton")).query();
    clickOn(saveButton);

    DialogPane saveDialog = lookup("#" + cssIdResources.getString("EndGamePopUp")).query();
    assertEquals(textResources.getString("FileName"), saveDialog.getContentText());

  }

  @Test
  public void testThemeImage() {
    ImageView themeImage = lookup("#" + cssResources.getString("ThemeImage")).query();
    try {
      Image baseImage = new Image(new FileInputStream("data/images/logos/Basic.png"));
      assertEquals(baseImage.getWidth(), themeImage.getImage().getWidth());
      assertEquals(baseImage.getHeight(), themeImage.getImage().getHeight());
    } catch (FileNotFoundException e) {
      Assertions.fail();
    }
  }

  @Test
  public void testInteractiveInputChange() {
    // Test that the interactive input changes on a cheat key press
    Text cardSelectText = lookup("#" + cssIdResources.getString("CardSelectText")).query();

    // change the top card on the discard pile to be a blue one
    controller.getModel().discardCard(new NumberCard("Blue", 2));
    runAsJFXAction(() -> controller.getUnoDisplay().render());

    // Put all ones in a player's hand
    press(KeyCode.N);

    // change them to be red
    press(KeyCode.R);

    // check: user should NOT be able to play a card
    assertEquals(textResources.getString("MustDraw"), cardSelectText.getText());

    // now change the cards to be blue
    press(KeyCode.B);

    // now user SHOULD be able to play a card
    assertEquals(textResources.getString("ChooseCard"), cardSelectText.getText());

  }

  @Test
  public void winGameTest() throws InterruptedException {
    press(KeyCode.X); // almost win the game, only need to play one card
    Node card = lookup("#" + cssIdResources.getString("UnoCard") + "0").query();
    clickOn(card);
    sleep(1000);

    try {
      Node back = lookup("#" + cssIdResources.getString("BackButton")).query();
    } catch (Exception e) {
      Assertions.fail();
    }
  }


}
