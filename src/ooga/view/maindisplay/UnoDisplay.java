package ooga.view.maindisplay;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ooga.controller.UnoDisplayController;
import ooga.util.Config;
import ooga.view.GameScreen;
import ooga.view.HandListDisplay;
import ooga.view.TurnInfoDisplay;
import ooga.view.deckdisplay.DeckDisplay;

public class UnoDisplay implements GameScreen {

  private static final String THEME_IMAGES_FILEPATH = "./data/images/logos/";

  private static final double THEME_IMAGE_WIDTH = 150;
  private static final double THEME_IMAGE_HEIGHT = 150;

  private static final String CSS_STYLE = "/ooga/resources/mainDisplay.css";
  public static final String BACK_BUTTON_CSS = "BackButton";

  private ResourceBundle languageResources;
  private ResourceBundle themeImageResources;

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
  public UnoDisplay(UnoDisplayController controller, String language) {
    this.controller = controller;
    languageResources = ResourceBundle.getBundle(String.format("ooga.resources.%s", language));
    themeImageResources = ResourceBundle.getBundle(
        String.format("ooga.view.maindisplay.ThemeFiles"));

    controller.getGameState().setSuppliers(() -> playCard(), () -> sendColor());

    unoDisplay = new BorderPane();
    myScene = new Scene(unoDisplay, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
    myScene.getStylesheets().add(UnoDisplay.class.getResource(CSS_STYLE).toExternalForm());

    this.turnDisplay = new TurnInfoDisplay(controller);
    this.handListDisplay = new HandListDisplay(controller, language);
    this.deckDisplay = new DeckDisplay(controller, language);

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
    center.getStyleClass().add("main_display_center_panel");

    Button goButton = new Button(languageResources.getString("PlayTurn"));
    goButton.getStyleClass().add("main_display_button");
    goButton.setOnAction(e -> playGame());
    center.getChildren()
        .addAll(deckDisplay.getDisplayableItem(), goButton, handListDisplay.getDisplayableItem());
    unoDisplay.setCenter(center);

    // left panel
    VBox left = new VBox();
    left.getStyleClass().add("main_display_left_panel");

    String themeImagePath =
        THEME_IMAGES_FILEPATH + themeImageResources.getString(controller.getGameVersion());
    try {
      ImageView themeImage = new ImageView(new Image(new FileInputStream(themeImagePath)));
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
    button.setOnAction(e -> controller.backButtonHandler());
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

    render();
  }

  private void playGame() {
    controller.getGameState().playTurn();
    render();
  }

  public void render() {
    deckDisplay.update();
    handListDisplay.update();
    turnDisplay.update();

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
        showError(languageResources.getString("InvalidFile"));
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
  private void showError(String alertMessage) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setContentText(alertMessage);
    alert.show();
  }

}
