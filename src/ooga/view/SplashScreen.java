package ooga.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import ooga.controller.SplashScreenController;
import ooga.model.gameState.GameStateViewInterface;
import ooga.util.Config;
import ooga.view.table.Table;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplashScreen implements GameScreen {

  private static final String TITLE = "WELCOME TO UNO";
  private static final String CSS_STYLE = "/ooga/resources/splashScreen.css";

  public static final String PLAY_CSS_ID = "PlayButton";

  private static final String TABLE_HEADER_LEFT = "Name";
  private static final String TABLE_HEADER_MIDDLE = "Player Type";
  private static final String TABLE_HEADER_RIGHT = "Delete";
  private static final String NAME_INPUT = "Enter a Name";
  private static final String TYPE_INPUT = "Player Type";
  private static final String POINTS_INPUT = "How Many Points To Win?";

  private static double CELL_HEIGHT = 30;
  private static double CELL_WIDTH = 70;

  private Table initialPlayers;
  private List<Button> rows;

  private boolean stackable;
  private int pointsToWin;
  private String gameType;
  private Map<String, String> players;

  SplashScreenController controller;

  public SplashScreen(SplashScreenController controller) {
    this.controller = controller;
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

    Text checkForUpdates = new Text(TITLE);
    checkForUpdates.getStyleClass().add("text-title");
    hbox.getChildren().add(checkForUpdates);

    return hbox;
  }

  private VBox addLeftNode() {
    VBox root = new VBox();
    root.getStyleClass().add("vbox");

    TextField points = new TextField();
    points.setPromptText(POINTS_INPUT);

    ChoiceBox<String> game = new ChoiceBox<>();
    game.setValue("UNO Game Type");
    game.getItems().add("Basic");
    game.getItems().add("Flip");
    game.getItems().add("Blast");
    game.setOnAction(e -> gameType = game.getValue());

    Button stackCards = new Button("Stack Cards? NO");
    stackCards.setOnAction(e -> stack(stackCards));

    Button setGame = new Button("Set Game Parameters");
    // TODO: uncomment the next line of code on the setGameParameters in UnoController is created
    setGame.setOnAction(e -> setGameHandler(points));

    Button loadExisting = new Button("Load Existing Game");
    loadExisting.setOnAction(e -> controller.loadExistingHandler());
    Button loadNew = new Button("Load New Game");
    loadNew.setOnAction(e -> chooseFile());

    ChoiceBox<String> language = new ChoiceBox<>();
    language.setValue("Language");
    language.getItems().add("English");
    language.getItems().add("Spanish");
    language.setOnAction(e -> controller.languageHandler());

    root.getChildren().addAll(points, game, stackCards, setGame, new Separator(), loadExisting, loadNew, language);

    return root;
  }

  private void setGameHandler(TextField points){
    Map<String, String> playerMap = new HashMap<>();
    int rows = initialPlayers.getNumRows();
    for(int i = 1; i < rows; i++){
      Text currentNameNode = (Text) initialPlayers.getCell(0, i);
      String currentName = currentNameNode.getText();
      Text currentTypeNode = (Text) initialPlayers.getCell(1, i);
      String currentType = currentTypeNode.getText();
      playerMap.put(currentName, currentType);
    }

    controller.setGameParameters(gameType, playerMap, points.getText(), stackable);
  }

  private void stack(Button button) {
    stackable = !stackable;
    if (!stackable) {
      button.setText("Stack Cards? NO");
    }
    else {
      button.setText("Stack Cards? YES");
    }
  }

  private void chooseFile() {
    FileChooser fileChooser = new FileChooser();
    //fileChooser.setInitialDirectory(new File(Paths.get(".").toAbsolutePath().normalize() + "/data"));
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
    fileChooser.getExtensionFilters().add(extFilter);
    File selectedFile = fileChooser.showOpenDialog(null);
    if(selectedFile != null){
      controller.loadNewHandler(selectedFile.getAbsolutePath());
    }
  }

  private HBox addBottomNode() {
    HBox root = new HBox();
    root.getStyleClass().add("hbox");

    Button playButton = new Button("Play");
    playButton.setId(PLAY_CSS_ID);
    playButton.setOnAction(e -> controller.playButtonHandler());

    root.getChildren().addAll(playButton);
    return root;
  }

  private void addNewPlayer(TextField nameInput, ChoiceBox<String> playerTypeInput) {

    String name = nameInput.getText();
    String playerType = playerTypeInput.getValue();
    if(playerType == "Human" || playerType == "CPU"){
      nameInput.clear();

      Button deleteButton = new Button("-");
      deleteButton.getStyleClass().add("delete-button");
      rows.add(deleteButton);

      int currentRow = initialPlayers.getNumRows();
      deleteButton.setOnAction(e -> {
        delete(currentRow);
        for (int i=currentRow-1; i<rows.size(); i++) {
          int finalI = i;
          rows.get(i).setOnAction(var -> delete(finalI +1));
        }
      });

      initialPlayers.addRow();
      initialPlayers.setCell(0, initialPlayers.getNumRows()-1, new Text(name));
      initialPlayers.setCell(1, initialPlayers.getNumRows()-1, new Text(playerType));
      initialPlayers.setCell(2, initialPlayers.getNumRows()-1, deleteButton);
    }

    else{
      sendAlert("Please Select Player Type");
    }
  }

  private void delete(int currentRow) {
    initialPlayers.deleteRow(currentRow);
    rows.remove(currentRow-1);
  }

  private VBox createRightNode() {
    VBox table = new VBox();
    table.getStyleClass().add("vbox");

    TextField nameInput = new TextField();
    nameInput.setPromptText(NAME_INPUT);

    ChoiceBox<String> playerTypeInput = new ChoiceBox<>();
    playerTypeInput.setValue(TYPE_INPUT);
    playerTypeInput.getItems().add("Human");
    playerTypeInput.getItems().add("CPU");

    Button addPlayer = new Button("Add New Player");
    addPlayer.setOnAction(e -> addNewPlayer(nameInput, playerTypeInput));

    table.getChildren().addAll(nameInput, playerTypeInput, addPlayer, new Separator(), initialPlayers.getDisplayableItem());

    return table;
  }

  private void initializeTable() {
    initialPlayers = new Table(1, 3, CELL_WIDTH, CELL_HEIGHT, "CreatePlayers");
    initialPlayers.setCell(0, 0, new Text(TABLE_HEADER_LEFT));
    initialPlayers.setCell(1, 0, new Text(TABLE_HEADER_MIDDLE));
    initialPlayers.setCell(2, 0, new Text(TABLE_HEADER_RIGHT));
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
