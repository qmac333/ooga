package ooga.view;

import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ooga.controller.UnoDisplayController;
import ooga.util.Config;
import ooga.view.deckdisplay.DeckDisplay;

public class UnoDisplay implements GameScreen {

  private static final String CSS_STYLE = "/ooga/resources/mainDisplay.css";

  public static final String BACK_BUTTON_CSS = "BackButton";
  public static final double SECONDS_BETWEEN_TURNS = 5;

  private UnoDisplayController controller;
  private TurnInfoDisplay turnDisplay;
  private HandListDisplay handListDisplay;
  private DeckDisplay deckDisplay;

  private Scene myScene;

  private BorderPane unoDisplay;

  /**
   * initializes data structures and saves the given controller
   *
   * @param controller a variable that provides access to controller methods
   */
  public UnoDisplay(UnoDisplayController controller) {
    this.controller = controller;

    controller.getGameState().createDeck(Map.of("DrawFour", () -> sendColor(), "Wild", () -> sendColor()));
    // send suppliers down to the model
    try {
      controller.getGameState().createPlayers(() -> playCard());
    } catch (Exception e) {
      e.getMessage();
    }

    unoDisplay = new BorderPane();
    myScene = new Scene(unoDisplay, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
    myScene.getStylesheets().add(UnoDisplay.class.getResource(CSS_STYLE).toExternalForm());

    this.turnDisplay = new TurnInfoDisplay(controller);
    this.handListDisplay = new HandListDisplay(controller);
    this.deckDisplay = new DeckDisplay(controller);

    createScene();
  }

  /**
   * creates the Scene for the display
   *
   * @return the current scene that we are displaying
   */
  public Scene setScene() {
    return myScene;
  }

  /**
   * provides a user-friendly way for errors to be displayed controller needs to call this method
   * when errors are encountered
   *
   * @param e the exception that was thrown from the error
   */
  public void showError(Exception e) {

  }

  private void createScene() {
    // center panel
    VBox center = new VBox();
    center.getStyleClass().add("main_display_center_panel");

    Button goButton = new Button("Choose Card");
    goButton.getStyleClass().add("main_display_button");
    goButton.setOnAction(e -> playGame());
    center.getChildren()
        .addAll(deckDisplay.getDisplayableItem(), goButton, handListDisplay.getDisplayableItem());
    unoDisplay.setCenter(center);

    // left panel
    VBox left = new VBox();
    left.getStyleClass().add("main_display_left_panel");

    Button button = new Button("Back");
    button.getStyleClass().add("main_display_button");
    button.setId(BACK_BUTTON_CSS);
    button.setOnAction(e -> controller.backButtonHandler());
    left.getChildren().add(button);

    unoDisplay.setLeft(left);

    // right panel
    VBox right = new VBox();
    right.getStyleClass().add("main_display_right_panel");
    right.getChildren().add(turnDisplay.getDisplayableItem());
    unoDisplay.setRight(right);
  }

  private void playGame() {
    controller.getGameState().playTurn();
  }

  /**
   * Called by the HumanPlayer class when the user has to decide which card to play.
   *
   * @return the index of the card in the user's hand to play.
   */
  private int playCard() {

    int indexCard = handListDisplay.selectCard();

    return indexCard;
  }

  private String sendColor() {
    return handListDisplay.wildPopUp();
  }

}
