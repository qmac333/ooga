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

  private ResourceBundle languageResources;

  private Table initialPlayers;
  private List<Button> rows;
  private Text readyIndicator;

  private boolean stackable;
  private String gameType;
  private Map<String, String> playerMap;

  SplashScreenController controller;

  public SplashScreen(SplashScreenController controller, String language) {
    this.controller = controller;
    languageResources = ResourceBundle.getBundle(String.format("ooga.resources.%s", language));
    initializeTable();
    rows = new ArrayList<>();
    playerMap = new HashMap<>();
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

    Button loadNew = new Button(languageResources.getString("LoadNew"));
    loadNew.setId(LOAD_NEW_GAME_CSS);
    loadNew.setOnAction(e -> chooseFile());

    root.getChildren().add(loadNew);

    return root;
  }

  private void setGameHandler(TextField points) {
    try{
      int pointsToWin = Integer.parseInt(points.getText());
      boolean successfulSetup = controller.setGameParameters(gameType, playerMap, pointsToWin, stackable);
      if(successfulSetup){
        readyIndicator.setText(languageResources.getString("SetParametersManual"));
      }
      else{
        showError(languageResources.getString("ValidValues"));
      }
    }
    catch(NumberFormatException e){
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
      if(successfulLoad){
        readyIndicator.setText(languageResources.getString("SetParametersFile"));
      }
      else{
        showError(languageResources.getString("BadFileFormat"));
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
    // TODO: create Mod dropdown (with default value of "Traditional") and retrieve value from that dropdown right here before passing it controller.playNewGame()
    String mod = "Traditional";
    boolean successfulPlay = controller.playNewGame(mod);
    if(!successfulPlay){
      showError(languageResources.getString("PlayButtonEarly"));
    }
  }

  private void addNewPlayer(TextField nameInput, ChoiceBox<String> playerTypeInput) {
    // TODO: Error checking, restrict name to be 15 characters long maximum
    String name = nameInput.getText();
    String playerType = playerTypeInput.getValue();
    if (playerType == "Human" || playerType == "CPU") {
      if(!playerMap.containsKey(name)){
        if(playerMap.size() < 11){
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
          playerMap.put(name, playerType);
        }
        else{
          showError(languageResources.getString("MaxPlayers"));
        }
      }
      else{
        showError(languageResources.getString("DuplicateName"));
      }
    } else {
      showError(languageResources.getString("SelectType"));
    }
  }

  private void delete(int currentRow) {
    Text currentNameNode = (Text) initialPlayers.getCell(0, currentRow);
    String currentName = currentNameNode.getText();
    playerMap.remove(currentName);
    initialPlayers.deleteRow(currentRow);
    rows.remove(currentRow - 1);
  }

  private VBox createRightNode() {
    VBox table = new VBox();
    table.getStyleClass().add("vbox");

    TextField points = new TextField();
    points.setPromptText(languageResources.getString("PointsInput"));

    ChoiceBox<String> game = new ChoiceBox<>();
    game.setValue(languageResources.getString("GameHeader"));
    game.getItems().add(languageResources.getString("Basic"));
    game.getItems().add(languageResources.getString("Flip"));
    game.getItems().add(languageResources.getString("Blast"));
    game.setOnAction(e -> gameType = translateToEnglish(game.getValue()));

    Button stackCards = new Button(languageResources.getString("NoStack"));
    stackCards.setOnAction(e -> stack(stackCards));

    Button setGame = new Button(languageResources.getString("GameParameters"));
    setGame.setOnAction(e -> setGameHandler(points));

    TextField nameInput = new TextField();
    nameInput.setPromptText(languageResources.getString("NameInput"));

    ChoiceBox<String> playerTypeInput = new ChoiceBox<>();
    playerTypeInput.setValue(languageResources.getString("TypeInput"));
    playerTypeInput.getItems().add("Human");
    playerTypeInput.getItems().add("CPU");

    Button addPlayer = new Button(languageResources.getString("AddPlayer"));
    addPlayer.setOnAction(e -> addNewPlayer(nameInput, playerTypeInput));

    table.getChildren().addAll(points, game, stackCards, new Separator(),
            nameInput, playerTypeInput, addPlayer, new Separator(), initialPlayers.getDisplayableItem(), setGame);

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

  private String translateToEnglish(String value) {
    for (String key : languageResources.keySet()) {
      if (Objects.equals(languageResources.getString(key), value)) {
        return key;
      }
    }
    return null;
  }

  // displays alert/error message to the user
  private void showError(String alertMessage) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setContentText(alertMessage);
    alert.show();
  }

}
