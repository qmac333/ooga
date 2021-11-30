package ooga.view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import ooga.controller.SplashScreenController;
import ooga.util.Config;
import ooga.view.table.Table;

import java.io.File;
import java.util.*;

public class SplashScreen implements GameScreen {

  private static final String CSS_STYLE = "/ooga/resources/splashScreen.css";

  public static final String PLAY_CSS_ID = "PlayButton";
  public static final String LOAD_NEW_GAME_CSS = "LoadNewGame";

  private static double CELL_HEIGHT = 30;
  private static double CELL_WIDTH = 100;

  protected ResourceBundle languageResources;

  private Table initialPlayers;
  private List<Button> rows;
  private Text readyIndicator;

  private boolean stackable;
  private String gameType;
  private Map<String, String> players;

  SplashScreenController controller;

  public SplashScreen(SplashScreenController controller, String language) {
    this.controller = controller;
    languageResources = ResourceBundle.getBundle(String.format("ooga.resources.%s", language));
    initializeTable();
    rows = new ArrayList<>();
    players = new HashMap<>();
  }

  public Scene setScene() {
    BorderPane borderPane = new BorderPane();
    borderPane.setTop(addTopNode());
    borderPane.setLeft(addLeftNode());
    borderPane.setBottom(addBottomNode());
    borderPane.setRight(createRightNode());

    Scene scene = new Scene(borderPane, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
    scene.getStylesheets()
        .add(SplashScreen.class.getResource(CSS_STYLE).toExternalForm());
    return scene;
  }

  private HBox addTopNode() {
    HBox hbox = new HBox();
    hbox.getStyleClass().add("hbox");

    Text checkForUpdates = new Text(languageResources.getString("Title"));
    checkForUpdates.getStyleClass().add("text-title");
    hbox.getChildren().add(checkForUpdates);

    return hbox;
  }

  private VBox addLeftNode() {
    VBox root = new VBox();
    root.getStyleClass().add("vbox");

    TextField points = new TextField();
    points.setPromptText(languageResources.getString("PointsInput"));

    ChoiceBox<String> game = new ChoiceBox<>();
    game.setValue(languageResources.getString("GameHeader"));
    game.getItems().add(languageResources.getString("Original"));
    game.getItems().add(languageResources.getString("Flip"));
    game.getItems().add(languageResources.getString("Blast"));
    game.setOnAction(e -> gameType = game.getValue());

    Button stackCards = new Button(languageResources.getString("NoStack"));
    stackCards.setOnAction(e -> stack(stackCards));

    Button setGame = new Button(languageResources.getString("GameParameters"));
    setGame.setOnAction(e -> setGameHandler(points));

    Button loadNew = new Button(languageResources.getString("LoadNew"));
    loadNew.setId(LOAD_NEW_GAME_CSS);
    loadNew.setOnAction(e -> chooseFile());
    Button loadExisting = new Button(languageResources.getString("LoadExisting"));
    loadExisting.setOnAction(e -> controller.loadExistingFile());

    root.getChildren()
        .addAll(points, game, stackCards, setGame, new Separator(), loadNew, loadExisting);

    return root;
  }

  private void setGameHandler(TextField points) {
    Map<String, String> playerMap = new HashMap<>();
    int rows = initialPlayers.getNumRows();
    for (int i = 1; i < rows; i++) {
      Text currentNameNode = (Text) initialPlayers.getCell(0, i);
      String currentName = currentNameNode.getText();
      Text currentTypeNode = (Text) initialPlayers.getCell(1, i);
      String currentType = currentTypeNode.getText();
      playerMap.put(currentName, currentType);
    }

    try{
      int pointsToWin = Integer.parseInt(points.getText());
      boolean successfulSetup = controller.setGameParameters(gameType, playerMap, pointsToWin, stackable);
      if(successfulSetup){
        readyIndicator.setText("Game Parameters Set (Manual)");
      }
      else{
        sendAlert("Please Input Valid Values For All Four Game Parameters");
      }
    }
    catch(NumberFormatException e){
      sendAlert("Please Input a Numeric Value in the Points to Win Field");
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
      boolean successfulLoad = controller.loadNewFile(selectedFile.getAbsolutePath());
      if(successfulLoad){
        readyIndicator.setText("Game Parameters Set (Loaded File)");
      }
      else{
        sendAlert("Unrecognized File Format");
      }
    }
  }

  private VBox addBottomNode() {
    VBox root = new VBox();
    root.getStyleClass().add("hbox");

    Button playButton = new Button(languageResources.getString("Play"));
    playButton.setId(PLAY_CSS_ID);
    playButton.setOnAction(e -> playNewGame());

    readyIndicator = new Text("");

    root.getChildren().addAll(readyIndicator, playButton);
    return root;
  }

  private void playNewGame(){
    boolean successfulPlay = controller.playNewGame();
    if(!successfulPlay){
      sendAlert("Please Load a Configuration File or Manually Input Parameters");
    }
  }

  private void addNewPlayer(TextField nameInput, ChoiceBox<String> playerTypeInput) {

    String name = nameInput.getText();
    String playerType = playerTypeInput.getValue();
    if (playerType == "Human" || playerType == "CPU") {
      nameInput.clear();

      Button deleteButton = new Button("-");
      deleteButton.getStyleClass().add("delete-button");
      rows.add(deleteButton);

      int currentRow = initialPlayers.getNumRows();
      deleteButton.setOnAction(e -> {
        delete(currentRow);
        for (int i = currentRow - 1; i < rows.size(); i++) {
          int finalI = i;
          rows.get(i).setOnAction(var -> delete(finalI + 1));
        }
      });

      initialPlayers.addRow();
      initialPlayers.setCell(0, initialPlayers.getNumRows() - 1, new Text(name));
      initialPlayers.setCell(1, initialPlayers.getNumRows() - 1, new Text(playerType));
      initialPlayers.setCell(2, initialPlayers.getNumRows() - 1, deleteButton);
    } else {
      sendAlert("Please Select Player Type");
    }
  }

  private void delete(int currentRow) {
    initialPlayers.deleteRow(currentRow);
    rows.remove(currentRow - 1);
  }

  private VBox createRightNode() {
    VBox table = new VBox();
    table.getStyleClass().add("vbox");

    TextField nameInput = new TextField();
    nameInput.setPromptText(languageResources.getString("NameInput"));

    ChoiceBox<String> playerTypeInput = new ChoiceBox<>();
    playerTypeInput.setValue(languageResources.getString("TypeInput"));
    playerTypeInput.getItems().add("Human");
    playerTypeInput.getItems().add("CPU");

    Button addPlayer = new Button(languageResources.getString("AddPlayer"));
    addPlayer.setOnAction(e -> addNewPlayer(nameInput, playerTypeInput));

    table.getChildren().addAll(nameInput, playerTypeInput, addPlayer, new Separator(),
        initialPlayers.getDisplayableItem());

    return table;
  }

  private void initializeTable() {
    initialPlayers = new Table(1, 3, CELL_WIDTH, CELL_HEIGHT, "CreatePlayers");
    initialPlayers.setCell(0, 0, new Text(languageResources.getString("TableHeaderLeft")));
    initialPlayers.setCell(1, 0, new Text(languageResources.getString("TableHeaderMiddle")));
    initialPlayers.setCell(2, 0, new Text(languageResources.getString("TableHeaderRight")));
  }

  private void initDynamicView() {

  }

  // displays alert/error message to the user
  private void sendAlert(String alertMessage) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setContentText(alertMessage);
    alert.show();
  }

}
