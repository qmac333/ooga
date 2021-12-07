package ooga.view.maindisplay;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ooga.controller.interfaces.UnoDisplayController;
import ooga.model.player.player.ViewPlayerInterface;
import ooga.util.Config;
import ooga.util.Log;
import ooga.view.GameScreen;
import ooga.view.HandListDisplay;
import ooga.view.TurnInfoDisplay;
import ooga.view.deckdisplay.DeckDisplay;

public class BasicUnoDisplay implements GameScreen {

  private static final String THEME_IMAGES_FILEPATH = "./data/images/logos/";

  private static final double THEME_IMAGE_WIDTH = 150;
  private static final double THEME_IMAGE_HEIGHT = 150;

  private static final int INTERACTIVE_NODES_INDEX = 1;

  public static final String BACK_BUTTON_CSS = "BackButton";
  public static final String THEME_IMAGE_CSS = "ThemeImage";
  public static final String PLAY_TURN_BUTTON_CSS = "PlayTurn";
  private static final String LOG_FILE = ".\\data\\logMessages.txt";

  private ResourceBundle languageResources;
  private ResourceBundle themeImageResources;

  private UnoDisplayController controller;
  private TurnInfoDisplay turnDisplay;
  private HandListDisplay handListDisplay;
  private DeckDisplay deckDisplay;

  private Scene myScene;

  private BorderPane unoDisplay;
  private Pane centerPanel;
  private int centerPanelBaseNodes; // record the base number of nodes in center panel without prompting for user input


  private Button playTurnButton; // for playing computer's turn
  private Text cardSelectText;

  private Log log;

  /**
   * initializes data structures and saves the given controller
   *
   * @param controller a variable that provides access to controller methods
   */
  public BasicUnoDisplay(UnoDisplayController controller, String language, String cssFile) {
    this.controller = controller;
    languageResources = ResourceBundle.getBundle(String.format("ooga.resources.%s", language));
    themeImageResources = ResourceBundle.getBundle(
        String.format("ooga.view.maindisplay.ThemeFiles"));

    controller.getGameState().setSuppliers(() -> playCard(), () -> sendColor());

    unoDisplay = new BorderPane();
    myScene = new Scene(unoDisplay, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
    myScene.getStylesheets().add(BasicUnoDisplay.class.getResource(cssFile).toExternalForm());

    this.turnDisplay = new TurnInfoDisplay(controller, language);
    this.handListDisplay = new HandListDisplay(controller, () -> finishTurn(), language);
    this.deckDisplay = new DeckDisplay(controller, language);
  }

  /**
   * creates the Scene for the display
   *
   * @return the current scene that we are displaying
   */
  public Scene setScene() {
    createScene();
    render();
    changeInteractiveInput();
    return myScene;
  }

  protected void createScene() {

    cardSelectText = new Text();
    cardSelectText.getStyleClass().add("text");

    playTurnButton = new Button(languageResources.getString("PlayTurn"));
    playTurnButton.getStyleClass().add("main_display_button");
    playTurnButton.setId(PLAY_TURN_BUTTON_CSS);
    playTurnButton.setOnAction(e -> playComputerTurn());

    // center panel
    VBox center = new VBox();
    center.getStyleClass().add("main_display_center_panel");

    center.getChildren()
        .addAll(deckDisplay.getDisplayableItem(), handListDisplay.getDisplayableItem());
    centerPanelBaseNodes = center.getChildren().size();
    unoDisplay.setCenter(center);
    centerPanel = center;

    // left panel
    VBox left = new VBox();
    left.getStyleClass().add("main_display_left_panel");

    String themeImagePath =
        THEME_IMAGES_FILEPATH + themeImageResources.getString(controller.getGameVersion());
    try {
      ImageView themeImage = new ImageView(new Image(new FileInputStream(themeImagePath)));
      themeImage.setId(THEME_IMAGE_CSS);
      themeImage.setFitHeight(THEME_IMAGE_HEIGHT);
      themeImage.setFitWidth(THEME_IMAGE_WIDTH);
      left.getChildren().add(themeImage);
    } catch (FileNotFoundException e) {
      //TODO: Use Logging to say image is not found
      System.out.println("Theme image not found.");
      System.exit(-1);
    }

    Button button = new Button(languageResources.getString("Back"));
    button.getStyleClass().add("main_display_button");
    button.setId(BACK_BUTTON_CSS);
    button.setOnAction(e -> controller.returnToSplashScreen());
    left.getChildren().add(button);

    Button saveButton = new Button(languageResources.getString("Save"));
    saveButton.getStyleClass().add("main_display_button");
    saveButton.setOnAction(e -> saveFile());
    left.getChildren().add(saveButton);

    unoDisplay.setLeft(left);

    // right panel
    VBox right = new VBox();
    right.getStyleClass().add("main_display_right_panel");
    right.getChildren().add(turnDisplay.getDisplayableItem());
    unoDisplay.setRight(right);
  }

  private void playComputerTurn() {
    controller.getGameState().playTurn();
    finishTurn();
  }

  private void finishTurn() {
    render();
    changeInteractiveInput();
    checkWinner();

  }

  // checks if the current player has won, and if so to restart the game
  private void checkWinner() {
    int currentPlayerIndex = controller.getGameState().getCurrentPlayer();
    ViewPlayerInterface currentPlayer = controller.getGameState().getPlayers().get(currentPlayerIndex);
    String playerName = currentPlayer.getName();
    int numPoints = currentPlayer.getPoints();

    if (controller.getGameState().getEndGame()) {
      String alertString = String.format(languageResources.getString("WinnerMessage"), playerName, numPoints);
      showMessage(alertString, AlertType.INFORMATION);
      controller.returnToSplashScreen();
    }
  }

  public void render() {
    deckDisplay.update();
    handListDisplay.update();
    turnDisplay.update();

  }

  private void changeInteractiveInput() {
    // remove any interactive input already on the screen
    if (centerPanel.getChildren().size() > centerPanelBaseNodes ) {
      centerPanel.getChildren().remove(INTERACTIVE_NODES_INDEX, centerPanel.getChildren().size() - centerPanelBaseNodes + 1);
    }

    if (controller.getGameState().userPicksCard()) { // player needs to select card
      setPromptText();
      centerPanel.getChildren().add(INTERACTIVE_NODES_INDEX, cardSelectText);
    }
    else {
      centerPanel.getChildren().add(INTERACTIVE_NODES_INDEX, playTurnButton);
    }
  }

  private void setPromptText() {
    String lookup = "";
    // can't play a card: prompt the user to click the draw button
    if (controller.getGameState().getValidIndexes().size() == 0) {
      lookup = "MustDraw";
    }
    else {
      lookup = "ChooseCard";
    }
    cardSelectText.setText(languageResources.getString(lookup));
  }

  private void saveFile() {
    TextInputDialog inputPopup = new TextInputDialog();
    inputPopup.setTitle("Save File");
    inputPopup.setHeaderText("File Destination: /data/configuration_files/Save Files");
    inputPopup.setContentText(languageResources.getString("FileName"));
    inputPopup.showAndWait();
    String filename = inputPopup.getResult();
    if (filename != null) {
      boolean successfulSave = controller.saveCurrentFile(filename);
      if (!successfulSave) {
        showMessage(languageResources.getString("InvalidFile"), AlertType.ERROR);
      }
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
  private void showMessage(String alertMessage, AlertType type) {
    Alert alert = new Alert(type);
    alert.setContentText(alertMessage);
    alert.showAndWait();
  }

  private void logError(String logMsg) throws IOException {
    log = new Log(LOG_FILE, this.getClass().toString());
    log.getLogger().setLevel(Level.WARNING);
    log.getLogger().warning(logMsg);
  }

  protected BorderPane getUnoDisplay() {
    return unoDisplay;
  }

}
