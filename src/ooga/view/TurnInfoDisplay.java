package ooga.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
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

  private static double CELL_HEIGHT = 30;
  private static double CELL_WIDTH = 70;


  private static Color HIGHLIGHT_COLOR = Color.YELLOW;

  private GameStateViewInterface gameState;
  //private Map<TurnInfoChanges, Consumer> changeHandlers;
  private Table playerTable;
  private VBox displayableItem;
  private Text displayText;

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

    displayableItem = new VBox();
    displayableItem.setAlignment(Pos.CENTER);

    initializeTable();
    displayText = new Text("Down");
    displayableItem.getChildren().add(displayText);

    timeline = new Timeline();
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(Config.REFRESH_RATE), e -> update()));
    timeline.play();
  }

  @Override
  public Node getDisplayableItem() {
    return displayableItem;
  }

  private void initializeTable() {
    playerTable = new Table(gameState.getPlayerNames().size(), 2, CELL_WIDTH, CELL_HEIGHT);
    displayableItem.getChildren().add(playerTable.getDisplayableItem());
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
    if (playerTable.getNumRows() < players.size()) {
      for (int i = playerTable.getNumRows(); i < players.size(); i++) {
        playerTable.addRow();
        playerTable.setCell(0, i, new Text(players.get(i)));
      }
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
      displayText.setText("Down");
    }
    else {
      displayText.setText("Up");
    }
  }
}
