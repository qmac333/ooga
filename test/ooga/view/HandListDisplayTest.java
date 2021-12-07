package ooga.view;

import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ooga.controller.UnoController;
import ooga.model.cards.NumberCard;
import ooga.model.cards.SkipCard;
import ooga.model.player.player.ViewPlayerInterface;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class HandListDisplayTest extends DukeApplicationTest {

  private UnoController controller;
  private ResourceBundle cssIdResources = ResourceBundle.getBundle("ooga.resources.CSSId");
  private ResourceBundle textResources = ResourceBundle.getBundle("ooga.resources.English");

  @Override
  public void start(Stage stage) {
    controller = new UnoController(stage);
    controller.setLanguage("English");
    controller.createSplashScreen("English");
    controller.loadFile("data/configuration_files/Test Files/validNewFile1.json");
    Button playButton = lookup("#" + cssIdResources.getString("PlayButton")).query();
    clickOn(playButton);
  }

  @Test
  public void clickOnNumberCardValid() {
    press(KeyCode.N); // get all number cards
    press(KeyCode.B); // turn all cards to blue
    controller.getModel().discardCard(new NumberCard("Blue", 9));
    runAsJFXAction(() -> controller.getUnoDisplay().render());

    Node card = lookup("#" + cssIdResources.getString("UnoCard") + "0").query();
    clickOn(card); // play the first card in your hand
    assertEquals("Blue", controller.getGameState().getDiscardPile().lastCardPushed().getMyColor());
    assertEquals(1, controller.getGameState().getDiscardPile().lastCardPushed().getNum());

  }

  @Test
  public void clickOnNumberCardInvalid() {
    press(KeyCode.N); // get all number cards
    press(KeyCode.R); // turn all cards to blue
    controller.getModel().discardCard(new NumberCard("Blue", 9));
    runAsJFXAction(() -> controller.getUnoDisplay().render());

    Node card = lookup("#" + cssIdResources.getString("UnoCard") + "0").query();
    clickOn(card); // play the first card in your hand

    DialogPane alertPopUp = lookup("#" + cssIdResources.getString("AlertPopUp")).query();
    assertEquals(textResources.getString("InvalidCardClicked"), alertPopUp.getContentText());

  }

  @Test
  public void clickOnWild() {
    press(KeyCode.W); // add a wild card
    runAsJFXAction(() -> controller.getUnoDisplay().render());

    // click on the wild card
    Node card = lookup("#" + cssIdResources.getString("UnoCard") + "7").query();
    clickOn(card);
    DialogPane dialog = lookup("#" + cssIdResources.getString("WildPopUp")).query();
    assertEquals(textResources.getString("ChooseColor"), dialog.getContentText());
  }

  @Test
  public void clickOnSkipCard() {
    press(KeyCode.S); // add a skip card
    controller.getModel().discardCard(new NumberCard("Blue", 1));
    runAsJFXAction(() -> controller.getUnoDisplay().render());

    Node card = lookup("#" + cssIdResources.getString("UnoCard") + "7").query();
    clickOn(card);

    // Should skip turn
    int currentPlayerIndex = controller.getGameState().getCurrentPlayer();
    String currentPlayerName = controller.getGameState().getPlayers().get(currentPlayerIndex)
        .getName();
    assertEquals("Quentin", currentPlayerName);

  }


}
