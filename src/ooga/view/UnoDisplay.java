package ooga.view;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;
import java.util.concurrent.locks.AbstractQueuedSynchronizer.ConditionObject;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import ooga.controller.UnoDisplayController;
import ooga.util.Config;

public class UnoDisplay implements GameScreen {

  public static final String BACK_BUTTON_CSS = "BackButton";
  public static final double SECONDS_BETWEEN_TURNS = 5;

  private UnoDisplayController controller;
  private TurnInfoDisplay turnDisplay;
  private HandListDisplay handListDisplay;
  private DeckDisplay deckDisplay;

  private Scene myScene;

  private BorderPane unoDisplay;

  private Timeline gameTimeline;

  private volatile boolean isAwaitingCardInput;
  private int cardPlayedIndex;

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

    this.turnDisplay = new TurnInfoDisplay(controller);
    this.handListDisplay = new HandListDisplay(controller, e -> takeCardInput(e));
    this.deckDisplay = new DeckDisplay(controller);
    unoDisplay = new BorderPane();

    isAwaitingCardInput = false;


    // create the Timeline for the game
    gameTimeline = new Timeline();
    gameTimeline.setCycleCount(Timeline.INDEFINITE);
    gameTimeline.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(SECONDS_BETWEEN_TURNS), e -> playGame()));
    gameTimeline.play();

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
    center.setAlignment(Pos.BOTTOM_CENTER);
    center.setSpacing(100);
    center.getChildren()
        .addAll(deckDisplay.getDisplayableItem(), handListDisplay.getDisplayableItem());
    unoDisplay.setCenter(center);

    // left panel
    VBox left = new VBox();
    left.setAlignment(Pos.CENTER);
    Button button = new Button("Back");
    button.setId(BACK_BUTTON_CSS);
    button.setOnAction(e -> controller.backButtonHandler());
    left.getChildren().add(button);
    unoDisplay.setLeft(left);

    // right panel
    VBox right = new VBox();
    right.setAlignment(Pos.CENTER_LEFT);
    right.getChildren().add(turnDisplay.getDisplayableItem());
    unoDisplay.setRight(right);

    Scene scene = new Scene(unoDisplay, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
    myScene = scene;
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
    // do not advance gameplay until a card is played
    gameTimeline.pause();
    isAwaitingCardInput = false;

    while (isAwaitingCardInput) {
    }

    gameTimeline.play();

    return 0;
  }

  private void takeCardInput(int index) {
    if (isAwaitingCardInput) {
      cardPlayedIndex = index;
      isAwaitingCardInput = false;
    }
  }

  private String sendColor() {
    return "red";
  }

}
