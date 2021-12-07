package ooga.view.gamescreens;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import ooga.controller.interfaces.SplashScreenController;
import ooga.util.Config;
import ooga.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class SplashScreen implements GameScreen {

  private static final String UNO_LOGO = "./data/images/logos/Basic.png";
  private static final String LOG_FILE = ".\\data\\logMessages.txt";
  private ResourceBundle languageResources;
  private ResourceBundle modLanguageResources;
  private ResourceBundle cssStylingResources;
  private ResourceBundle cssIdResources;

  private VBox tableDisplay;
  private Text parametersIndicator;
  private Text newGameIndicator;

  private TextField nameTextField;
  private TextField pointsTextField;
  private ChoiceBox<String> gameChoiceBox;
  private Button stackCardsButton;
  private TableView<Player> playerTable;


  private String modType;
  private boolean stackable;
  private String gameType;
  private Map<String, String> playerMap;

  private Scene scene;
  private boolean dark;
  private boolean unc;
  private boolean springColors;

  private Log log;

  SplashScreenController controller;

  public SplashScreen(SplashScreenController controller, String language) {
    this.controller = controller;
    languageResources = ResourceBundle.getBundle(String.format("ooga.resources.%s", language));
    modLanguageResources = ResourceBundle.getBundle(String.format("ooga.resources.mods.%s", language));
    cssStylingResources = ResourceBundle.getBundle("ooga.resources.CSSStyling");
    cssIdResources = ResourceBundle.getBundle("ooga.resources.CSSId");
    initializeTable();
    playerMap = new HashMap<>();
  }

  public Scene setScene() {
    BorderPane borderPane = new BorderPane();
    borderPane.setTop(createTopNode());
    borderPane.setLeft(createLeftNode());
    borderPane.setBottom(addBottomNode());
    borderPane.setRight(createRightNode());
    try {
      Image image = new Image(new FileInputStream(UNO_LOGO));
      ImageView imageView = new ImageView(image);
      imageView.setFitHeight(300);
      imageView.setFitWidth(300);
      borderPane.setCenter(imageView);
    } catch(Exception e) {

      try {
        logError("Uno Logo Image not found");
      } catch(Exception ignored) {

      }
    }

    scene = new Scene(borderPane, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
    scene.getStylesheets()
        .add(SplashScreen.class.getResource(String.format(cssStylingResources.getString("SplashScreen"))).toExternalForm());
    controller.setColorThemeFilepath(String.format(cssStylingResources.getString("Main")));
    return scene;
  }

  private HBox createTopNode() {
    HBox root = new HBox();
    root.getStyleClass().add("hbox");

    Text checkForUpdates = new Text(languageResources.getString("Title"));
    checkForUpdates.setId(cssIdResources.getString("SplashScreenTitleText"));
    checkForUpdates.getStyleClass().add("text-title");
    root.getChildren().add(checkForUpdates);

    return root;
  }

  private VBox createLeftNode() {
    VBox root = new VBox();
    root.getStyleClass().add("vbox");

    Button darkMode = new Button(languageResources.getString("DarkMode"));
    darkMode.setId(cssIdResources.getString("DarkMode"));
    darkMode.setOnAction(e -> changeColorMode(darkMode));

    Button schoolColors = new Button(languageResources.getString("UNC"));
    schoolColors.setId(cssIdResources.getString("UncColor"));
    schoolColors.setOnAction(e -> changeSchool(schoolColors));

    Button seasonColors = new Button(languageResources.getString("SpringColors"));
    seasonColors.setId(cssIdResources.getString("SeasonColor"));
    seasonColors.setOnAction(e -> changeSeason(seasonColors));

    Button loadNew = new Button(languageResources.getString("LoadNew"));
    loadNew.setId(cssIdResources.getString("LoadNewGame"));
    loadNew.setOnAction(e -> chooseFile());

    root.getChildren().addAll(darkMode, schoolColors, seasonColors, new Separator(), loadNew);

    return root;
  }

  private void changeSeason(Button button) {
    springColors = !springColors;
    if (springColors) {
      scene.getStylesheets().clear();
      scene.getStylesheets()
              .add(SplashScreen.class.getResource(String.format(cssStylingResources.getString("SplashScreenSpring"))).toExternalForm());
      button.setText(languageResources.getString("FallColors"));
      controller.setColorThemeFilepath(String.format(cssStylingResources.getString("MainSpring")));
    } else {
      scene.getStylesheets().clear();
      scene.getStylesheets()
              .add(SplashScreen.class.getResource(String.format(cssStylingResources.getString("SplashScreenFall"))).toExternalForm());
      button.setText(languageResources.getString("SpringColors"));
      controller.setColorThemeFilepath(String.format(cssStylingResources.getString("MainFall")));
    }
  }

  private void changeSchool(Button button) {
    unc = !unc;
    if (unc) {
      scene.getStylesheets().clear();
      scene.getStylesheets()
              .add(SplashScreen.class.getResource(String.format(cssStylingResources.getString("SplashScreenUnc"))).toExternalForm());
      button.setText(languageResources.getString("Duke"));
      controller.setColorThemeFilepath(String.format(cssStylingResources.getString("MainUnc")));

    } else {
      scene.getStylesheets().clear();
      scene.getStylesheets()
              .add(SplashScreen.class.getResource(String.format(cssStylingResources.getString("SplashScreenDuke"))).toExternalForm());
      button.setText(languageResources.getString("UNC"));
      controller.setColorThemeFilepath(String.format(cssStylingResources.getString("MainDuke")));

    }
  }

  private void changeColorMode(Button button) {
    dark = !dark;
    if (dark) {
      scene.getStylesheets().clear();
      scene.getStylesheets()
              .add(SplashScreen.class.getResource(String.format(cssStylingResources.getString("SplashScreenDark"))).toExternalForm());
      button.setText(languageResources.getString("LightMode"));
      controller.setColorThemeFilepath(String.format(cssStylingResources.getString("MainDark")));
    } else {
      scene.getStylesheets().clear();
      scene.getStylesheets()
              .add(SplashScreen.class.getResource(String.format(cssStylingResources.getString("SplashScreen"))).toExternalForm());
      button.setText(languageResources.getString("DarkMode"));
      controller.setColorThemeFilepath(String.format(cssStylingResources.getString("Main")));
    }
  }

  private void setGameHandler(TextField points) {
    try {
      int pointsToWin = Integer.parseInt(points.getText());
      boolean successfulSetup = controller.setGameParameters(gameType, playerMap, pointsToWin,
          stackable);
      if (successfulSetup) {
        parametersIndicator.setText(languageResources.getString("ManualParameters"));
        newGameIndicator.setText(languageResources.getString("NewGame"));
      } else {
        showError(languageResources.getString("ValidValues"));
      }
    } catch (NumberFormatException e) {
      showError(languageResources.getString("InvalidPoints"));
    }
  }

  private void stack(Button button) {
    stackable = !stackable;
    if (!stackable) {
      button.setText(languageResources.getString("NoStack"));
    } else {
      button.setText(languageResources.getString("YesStack"));
    }
  }

  private void chooseFile() {
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)",
        "*.json");
    fileChooser.getExtensionFilters().add(extFilter);
    File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
      boolean successfulLoad = controller.loadFile(selectedFile.getAbsolutePath());
      if (successfulLoad) {
        parametersIndicator.setText(languageResources.getString("LoadedParameters"));
        if(controller.getLoadedGameInProgress()){
          newGameIndicator.setText(languageResources.getString("InProgressGame"));
        }
        else{
          newGameIndicator.setText(languageResources.getString("NewGame"));
        }
        updateParameters();
      } else {
        showError(languageResources.getString("BadFileFormat"));
      }
    }
  }

  private void updateParameters(){
    gameType = controller.getGameVersion();
    playerMap = controller.getPlayerMap();
    int newPoints = controller.getPoints();
    boolean newStackable = controller.getStackable();

    gameChoiceBox.setValue(languageResources.getString(gameType));
    playerTable.getItems().clear();
    for(String key : playerMap.keySet()){
      playerTable.getItems().add(new Player(key, playerMap.get(key)));
    }
    pointsTextField.setText(newPoints + "");
    if(stackable != newStackable){
      stack(stackCardsButton);
    }
  }

  private VBox addBottomNode() {
    VBox root = new VBox();
    root.getStyleClass().add("hbox");

    Button playButton = new Button(languageResources.getString("Play"));
    playButton.getStyleClass().add("play-button");
    playButton.setId(cssIdResources.getString("PlayButton"));
    playButton.setOnAction(e -> playNewGame());

    newGameIndicator = new Text("");
    newGameIndicator.setId(cssIdResources.getString("NewGameText"));
    newGameIndicator.getStyleClass().add("ready-indicator");
    parametersIndicator = new Text("");
    parametersIndicator.getStyleClass().add("ready-indicator");

    root.getChildren().addAll(newGameIndicator, parametersIndicator, playButton);
    return root;
  }

  private void playNewGame() {
    boolean successfulPlay = controller.playNewGame(modType);
    if (!successfulPlay) {
      showError(languageResources.getString("PlayButtonEarly"));
    }
  }

  private void addNewPlayer(String name, String playerType) {
    if (playerType == languageResources.getString("Human") || playerType == languageResources.getString("CPU")) {
      if (!playerMap.containsKey(name)) {
        if (playerMap.size() < 11) {
          nameTextField.clear();
          playerTable.getItems().add(new Player(name, playerType));
          playerMap.put(name, playerType);
        } else {
          showError(languageResources.getString("MaxPlayers"));
        }
      } else {
        showError(languageResources.getString("DuplicateName"));
      }
    } else {
      showError(languageResources.getString("SelectType"));
    }
  }

  private VBox createRightNode() {
    VBox root = new VBox();
    root.getStyleClass().add("vbox");

    pointsTextField = new TextField();
    pointsTextField.setId(cssIdResources.getString("PointsText"));
    pointsTextField.setPromptText(languageResources.getString("PointsInput"));

    gameChoiceBox = new ChoiceBox<>();
    gameChoiceBox.setId(cssIdResources.getString("GameTypeChoice"));
    gameChoiceBox.setValue(languageResources.getString("GameHeader"));
    gameChoiceBox.getItems().add(languageResources.getString("Basic"));
    gameChoiceBox.getItems().add(languageResources.getString("Flip"));
    gameChoiceBox.getItems().add(languageResources.getString("Blast"));
    gameChoiceBox.setOnAction(e -> gameType = translateToEnglish(gameChoiceBox.getValue(), languageResources));

    ChoiceBox<String> modSelect = new ChoiceBox<>();
    modSelect.setId(cssIdResources.getString("ModSelect"));
    modType = "Traditional";
    modSelect.setValue(modLanguageResources.getString("Traditional"));
    for (String mod : modLanguageResources.keySet()) {
      modSelect.getItems().add(modLanguageResources.getString(mod));
    }
    modSelect.setOnAction(e -> {
      modType = translateToEnglish(modSelect.getValue(), modLanguageResources);
    });

    stackCardsButton = new Button(languageResources.getString("NoStack"));
    stackCardsButton.setOnAction(e -> stack(stackCardsButton));

    Button setGame = new Button(languageResources.getString("GameParameters"));
    setGame.setId(cssIdResources.getString("SetGame"));
    setGame.setOnAction(e -> setGameHandler(pointsTextField));

    nameTextField = new TextField();
    nameTextField.setId(cssIdResources.getString("NameText"));
    nameTextField.setPromptText(languageResources.getString("NameInput"));

    ChoiceBox<String> playerTypeInput = new ChoiceBox<>();
    playerTypeInput.setId(cssIdResources.getString("PlayerTypeInput"));
    playerTypeInput.setValue(languageResources.getString("TypeInput"));
    playerTypeInput.getItems().add(languageResources.getString("Human"));
    playerTypeInput.getItems().add(languageResources.getString("CPU"));

    Button addPlayer = new Button(languageResources.getString("AddPlayer"));
    addPlayer.setId(cssIdResources.getString("AddPlayerToGame"));
    addPlayer.setOnAction(e -> addNewPlayer(nameTextField.getText(), playerTypeInput.getValue()));

    root.getChildren().addAll(pointsTextField, gameChoiceBox, modSelect, stackCardsButton, new Separator(),
            nameTextField, playerTypeInput, addPlayer, new Separator(), tableDisplay, setGame);

    return root;
  }

  private void initializeTable() {
    tableDisplay = new VBox();
    tableDisplay.getStyleClass().add("vbox");
    playerTable = new TableView<>();
    playerTable.setPlaceholder(new Label(languageResources.getString("NothingYet")));

    TableColumn<Player, String> playerNameCol = new TableColumn<>(
        languageResources.getString("TableHeaderLeft"));
    TableColumn<Player, String> playerTypeCol = new TableColumn<>(
        languageResources.getString("TableHeaderMiddle"));
    playerNameCol.setMinWidth(150);
    playerTypeCol.setMinWidth(150);
    playerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    playerTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
    playerTable.getColumns().addAll(playerNameCol, playerTypeCol);

    Button deleteButton = new Button(languageResources.getString("Delete"));
    deleteButton.getStyleClass().add("delete-button");
    deleteButton.setOnMousePressed(e -> {
      Player selectedPlayer = playerTable.getSelectionModel().getSelectedItem();
      if (selectedPlayer != null) {
        playerTable.getItems().remove(selectedPlayer);
        playerMap.remove(selectedPlayer.getName());
      }
    });

    tableDisplay.getChildren().addAll(playerTable, deleteButton);
  }

  private String translateToEnglish(String value, ResourceBundle resources) {
    for (String key : resources.keySet()) {
      if (Objects.equals(resources.getString(key), value)) {
        return key;
      }
    }
    return null;
  }

  /**
   * Creates a popup error message to be seen by the user
   * @param alertMessage String of the message to be displayed
   */
  private void showError(String alertMessage) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setContentText(alertMessage);
    alert.show();
  }

  private void logError(String logMsg) throws IOException {
    log = new Log(LOG_FILE, this.getClass().toString());
    log.getLogger().setLevel(Level.WARNING);
    log.getLogger().warning(logMsg);
  }

  public class Player {

    private String playerType;
    private String name;

    public Player(String name, String type) {
      playerType = type;
      this.name = name;

    }

    public String getType() {
      return playerType;
    }

    public String getName() {
      return name;
    }


  }

}
