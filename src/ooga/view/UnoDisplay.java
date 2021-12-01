package ooga.view;

import java.io.IOException;
import java.util.Map;

import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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
      controller.getGameState().createPlayers(() -> playCard(), () -> sendColor());
    } catch (Exception e) {
      e.getMessage();
    }

    this.turnDisplay = new TurnInfoDisplay(controller);
    this.handListDisplay = new HandListDisplay(controller);
    this.deckDisplay = new DeckDisplay(controller);
    unoDisplay = new BorderPane();


    // create the Timeline for the game
//    gameTimeline = new Timeline();
//    gameTimeline.setCycleCount(Timeline.INDEFINITE);
//    gameTimeline.getKeyFrames()
//        .add(new KeyFrame(Duration.seconds(SECONDS_BETWEEN_TURNS), e -> playGame()));
//    gameTimeline.play();


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

    Button goButton = new Button("Play Turn");
    goButton.setOnAction(e -> playGame());
    left.getChildren().add(goButton);

    Button saveButton = new Button("Save");
    saveButton.setOnAction(e -> saveFile());
    left.getChildren().add(saveButton);
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

  private void saveFile(){
    try{
      TextInputDialog inputPopup = new TextInputDialog();
      inputPopup.setTitle("Save File");
      inputPopup.setHeaderText("Enter Desired Filename:");
      inputPopup.showAndWait();
      String filename = inputPopup.getResult();
      if(filename != null){
        controller.saveCurrentFile(filename);
      }
    }
    catch (IOException e){
      // TODO: this
      e.printStackTrace();
      showError(e.getMessage());
      //showError("Invalid File Name");
    }
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

  // displays alert/error message to the user
  private void showError(String alertMessage) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setContentText(alertMessage);
    alert.show();
  }

}
