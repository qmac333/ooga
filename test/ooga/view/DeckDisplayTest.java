package ooga.view;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.controller.UnoController;
import ooga.model.cards.NumberCard;
import ooga.view.deckdisplay.DeckDisplay;
import ooga.view.maindisplay.UnoDisplay;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckDisplayTest extends DukeApplicationTest {

  private UnoController controller;
  private UnoDisplay display;

  @Override
  public void start(Stage stage) {
    CardDisplay.initializeCards();
    controller = new UnoController(stage);
    controller.setLanguage("English");
    controller.createSplashScreen("English");
    controller.loadFile("data/configuration_files/Test Files/validNewFile1.json");
    Button playButton = lookup("#" + SplashScreen.PLAY_CSS_ID).query();
    clickOn(playButton);
  }

  @Test
  public void checkDeckDisplayNumCards() {
    // check that the text displayed on the screen listing the number of cards in the deck matches the actual number of cards in the deck
    Text numCardsText = lookup("#" + DeckDisplay.DECK_NUM_CARDS_TEXT_CSS).query();
    int numCards = 0;
    try {
      numCards = Integer.parseInt(numCardsText.getText());
    } catch (NumberFormatException e) {
      Assertions.fail(); // not a valid number of cards
    }

    assertEquals(controller.getGameState().getDeck().getNumCards(), numCards);

  }

  @Test
  public void checkDeckDisplayImage() {
    Parent viewCardVisual = lookup("#" + DeckDisplay.DECK_CARD_CSS).query();
    // check the image on the card, and confirm it is of the top side of an UNO card
    int numChildrenSize = viewCardVisual.getChildrenUnmodifiable().size();
    ImageView cardImage = null;
    try {
      cardImage = (ImageView) viewCardVisual.getChildrenUnmodifiable().get(numChildrenSize - 1);
    } catch (Exception e) {
      Assertions.fail();
    }

    assertEquals(cardImage.getImage(), CardDisplay.IMAGES.get("Top"));
  }

  @Test
  public void checkDiscardPileImage() {
    display = controller.getUnoDisplay();
    // find the card on top of the discard pile
    controller.getModel().discardCard(new NumberCard("red", 9));
    runAsJFXAction(() -> display.render());

    Parent viewCardVisual = lookup("#" + DeckDisplay.DISCARD_PILE_CSS).query();

    int numChildrenSize = viewCardVisual.getChildrenUnmodifiable().size();
    Rectangle cardColor = null;
    ImageView cardImage = null;
    try {
      cardImage = (ImageView) viewCardVisual.getChildrenUnmodifiable().get(numChildrenSize - 1);
      cardColor = (Rectangle) viewCardVisual.getChildrenUnmodifiable().get(numChildrenSize - 2);
    } catch (Exception e) {
      Assertions.fail();
    }

    assertEquals(cardImage.getImage(), CardDisplay.IMAGES.get("9"));
    assertEquals(cardColor.getFill(), Color.RED);
  }

  private void pause(double millis) {
    long init = System.currentTimeMillis();
    while (System.currentTimeMillis() < init + millis) {
      // spin
    }
  }

}
