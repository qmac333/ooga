package ooga.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ooga.controller.UnoDisplayController;
import ooga.model.gameState.GameStateViewInterface;
import ooga.util.Config;
import ooga.util.TurnInfoChanges;
import ooga.view.table.Table;

public class TurnInfoDisplay implements DisplayableItem {

  public static String ARROW_CSS = "GameplayDirection";

  private static double CELL_HEIGHT = 30;
  private static double CELL_WIDTH = 70;

  private static double ARROW_HEIGHT = 50;
  private static double ARROW_WIDTH = 50;


  private static Color HIGHLIGHT_COLOR = Color.YELLOW;

  private GameStateViewInterface gameState;

  private Table playerTable;
  private ImageView directionArrow;
  private HBox displayableItem;

  private Timeline timeline;
  private int prevCurrentPlayer;


  /**
   * Initialize a class that creates the display for the info on the current players and whose turn
   * it is.
   *
   * @param controller is a reference to the controller object to pass the consumer through to the
   *                   model
   */
  public TurnInfoDisplay(UnoDisplayController controller) {
    gameState = controller.getGameState();
    prevCurrentPlayer = 0;

    displayableItem = new HBox();
    displayableItem.setSpacing(20);

    initializeDirection();
    initializeTable();

    timeline = new Timeline();
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(Config.REFRESH_RATE), e -> update()));
    timeline.play();
  }

  @Override
  public Node getDisplayableItem() {
    return displayableItem;
  }

  public Table getTable() {
    return playerTable;
  }

  private void initializeTable() {
    playerTable = new Table(gameState.getPlayerNames().size(), 2, CELL_WIDTH, CELL_HEIGHT,
        "TurnInfo");
    displayableItem.getChildren().add(playerTable.getDisplayableItem());
  }

  private void initializeDirection() {
    try {
      directionArrow = new ImageView(new Image(new FileInputStream("./ooga_team05/data/images/Arrow.png")));
      directionArrow.setId(ARROW_CSS);
    } catch (FileNotFoundException e) {
      System.exit(-1); // TODO: use logging
    }


    directionArrow.setFitHeight(ARROW_HEIGHT);
    directionArrow.setFitWidth(ARROW_WIDTH);
    newDirectionChangeHandler();

    displayableItem.getChildren().add(directionArrow);

  }

  private void update() {
    playersChangeHandler();
    numCardsChangeHandler();
    currentPlayerChangeHandler();
    newDirectionChangeHandler();
  }

  // ASSUME that players can be added, and not deleted
  private void playersChangeHandler() {
    List<String> players = gameState.getPlayerNames();
    for (int i = 0; i < players.size(); i++) {
      if (i >= playerTable.getNumRows()) {
        playerTable.addRow();
      }
      playerTable.setCell(0, i, new Text(players.get(i)));
    }
  }

  private void numCardsChangeHandler() {
    List<Integer> counts = gameState.getCardCounts();
    for (int i = 0; i < counts.size(); i++) {
      playerTable.setCell(1, i, new Text(String.valueOf(counts.get(i))));
    }
  }

  private void currentPlayerChangeHandler() {

    int currentPlayer = gameState.getCurrentPlayer();
    for (int i = 0; i < playerTable.getNumCols(); i++) {
      playerTable.setColor(i, prevCurrentPlayer, Table.DEFAULT_COLOR);
      playerTable.setColor(i, currentPlayer, HIGHLIGHT_COLOR);
    }
    prevCurrentPlayer = currentPlayer;
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
