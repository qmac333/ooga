package ooga.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
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
import java.util.*;

public class SplashScreen implements GameScreen {

  private static final String CSS_STYLE = "/ooga/resources/splashScreen.css";

  public static final String PLAY_CSS_ID = "PlayButton";

  private static double CELL_HEIGHT = 30;
  private static double CELL_WIDTH = 70;

  protected ResourceBundle languageResources;

  private Table initialPlayers;
  private List<Button> rows;

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
    game.setValue("UNO Game Type");
    game.getItems().add("Original");
    game.getItems().add("UNO Flip");
    game.getItems().add("UNO Blast");
    game.setOnAction(e -> gameType = game.getValue());

    Button stackCards = new Button("Stack Cards? NO");
    stackCards.setOnAction(e -> stack(stackCards));

    Button setGame = new Button("Set Game Parameters");
    // TODO: uncomment the next line of code on the setGameParameters in UnoController is created
//    setGame.setOnAction(e -> controller.setGameParameters(points.getText(), gameType, stackable));

    Button loadExisting = new Button("Load Existing Game");
    loadExisting.setOnAction(e -> controller.loadExistingHandler());
    Button loadNew = new Button("Load New Game");
    loadNew.setOnAction(e -> chooseFile());

    root.getChildren().addAll(points, game, stackCards, setGame, new Separator(), loadExisting, loadNew);

    return root;
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

  private void addNewPlayer(TextField nameInput, TextField playerTypeInput) {
    String name = nameInput.getText();
    String playerType = playerTypeInput.getText();
    nameInput.clear();
    playerTypeInput.clear();

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

  private void delete(int currentRow) {
    initialPlayers.deleteRow(currentRow);
    rows.remove(currentRow-1);
  }

  private VBox createRightNode() {
    VBox table = new VBox();
    table.getStyleClass().add("vbox");

    TextField nameInput = new TextField();
    nameInput.setPromptText(languageResources.getString("NameInput"));

    TextField playerTypeInput = new TextField();
    playerTypeInput.setPromptText(languageResources.getString("TypeInput"));

    Button addPlayer = new Button("Add New Player");
    addPlayer.setOnAction(e -> addNewPlayer(nameInput, playerTypeInput));

    table.getChildren().addAll(nameInput, playerTypeInput, addPlayer, new Separator(), initialPlayers.getDisplayableItem());

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

}
