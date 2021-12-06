package ooga.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import ooga.controller.UnoDisplayController;
import ooga.model.gameState.GameStateViewInterface;
import ooga.model.player.player.ViewPlayerInterface;

public class TurnInfoDisplay implements DisplayableItem {

  public static String ARROW_CSS = "GameplayDirection";
  public static String PLAYER_TABLE_CSS = "PlayerTable";
  private static double ARROW_HEIGHT = 50;
  private static double ARROW_WIDTH = 50;

  private GameStateViewInterface gameState;
  private ResourceBundle languageResources;

  private TableView<ViewPlayerInterface> playerTable;
  private ImageView directionArrow;
  private HBox displayableItem;

  private String currentPlayer;


  /**
   * Initialize a class that creates the display for the info on the current players and whose turn
   * it is.
   *
   * @param controller is a reference to the controller object to pass the consumer through to the
   *                   model
   */
  public TurnInfoDisplay(UnoDisplayController controller, String language) {
    gameState = controller.getGameState();
    languageResources = ResourceBundle.getBundle(String.format("ooga.resources.%s", language));

    displayableItem = new HBox();
    displayableItem.getStyleClass().add("turn_info_main_display");

    currentPlayer = "";

    initializeDirection();
    initializeTable();

  }

  @Override
  public Node getDisplayableItem() {
    return displayableItem;
  }

  private void initializeTable() {
    playerTable = new TableView<>();
    playerTable.setId(PLAYER_TABLE_CSS);
    playerTable.setSelectionModel(null);
    playerTable.setMaxSize(200, 300);

    TableColumn<ViewPlayerInterface, String> playerNameCol = new TableColumn<>(
        languageResources.getString("TurnDisplayNameHeader"));
    TableColumn<ViewPlayerInterface, Integer> playerCardsCol = new TableColumn<>(
        languageResources.getString("TurnDisplayCardsHeader"));
    TableColumn<ViewPlayerInterface, Integer> playerPointsCol = new TableColumn<>(
        languageResources.getString("TurnDisplayPointsHeader"));

    playerNameCol.setMinWidth(100);
    playerCardsCol.setMinWidth(50);
    playerPointsCol.setMinWidth(50);
    playerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    playerCardsCol.setCellValueFactory(new PropertyValueFactory<>("handSize"));
    playerPointsCol.setCellValueFactory(new PropertyValueFactory<>("points"));

    playerTable.getColumns().addAll(playerNameCol, playerCardsCol, playerPointsCol);

    playerTable.setRowFactory(
        param -> new TableRow<ViewPlayerInterface>() {
          @Override
          protected void updateItem(ViewPlayerInterface player, boolean empty) {
            super.updateItem(player, empty);
            if ((!empty) && currentPlayer.equals(player.getName())) {
              setStyle("-fx-background-color: yellow");
            }
            else {
              setStyle("");
            }
          }

        });

    displayableItem.getChildren().add(playerTable);
  }

  private void initializeDirection() {
    try {
      directionArrow = new ImageView(new Image(new FileInputStream("data/images/Arrow.png")));
      directionArrow.getStyleClass().add("turn_info_gameplay_direction_arrow");
      directionArrow.setId(ARROW_CSS);
    } catch (FileNotFoundException e) {
      System.exit(-1); // TODO: use logging
    }


    directionArrow.setFitHeight(ARROW_HEIGHT);
    directionArrow.setFitWidth(ARROW_WIDTH);
    newDirectionChangeHandler();

    displayableItem.getChildren().add(directionArrow);

  }

  public void update() {
    tableChangeHandler();
    newDirectionChangeHandler();
  }

  private void tableChangeHandler() {

    int currentPlayerIndex = gameState.getCurrentPlayer();
    currentPlayer = gameState.getPlayers().get(currentPlayerIndex).getName();


    playerTable.getItems().clear();
    List<ViewPlayerInterface> players = gameState.getPlayers();
    playerTable.getItems().addAll(players);

  }

  private void newDirectionChangeHandler() {
    int direction = gameState.getGameplayDirection();
    if (direction == 1) {
      directionArrow.setRotate(90); // rotate arrow to face downward
    }
    else {
      directionArrow.setRotate(-90); // rotate arrow to face upward
    }
  }
}
