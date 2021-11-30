package ooga.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import ooga.controller.UnoDisplayController;
import ooga.model.cards.ViewCardInterface;
import ooga.model.gameState.GameStateViewInterface;
import ooga.util.Config;

public class HandListDisplay implements DisplayableItem {

  private static final String[] WILDCOLORS = {"Red", "Blue", "Green", "Yellow"};

  private static final int[] NUMBERS  = {1,2,3,4,5,6,7};

  private GameStateViewInterface gameState;
  private UnoDisplayController controller;
  private HBox handList;
  private List<ViewCardInterface> currentCards;
  private List<Node> cardDisplay;
  private Timeline timeline;


  /**
   * Initialize a class that creates the display for an UNO hand.
   *
   * @param controller is a reference to the controller object to pass the consumer through to the
   *                   model
   */
  public HandListDisplay(UnoDisplayController controller) {
    this.controller = controller;
    gameState = controller.getGameState();
    handList = new HBox();
    handList.setAlignment(Pos.CENTER);

    cardDisplay = new ArrayList<>();
    currentCards = gameState.getCurrentPlayerCards();

    timeline = new Timeline();
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(Config.REFRESH_RATE), e -> update()));
    timeline.play();
  }

  private void update() {
    handList.getChildren().clear();
    cardDisplay.clear();
    currentCards = gameState.getCurrentPlayerCards();
    for (ViewCardInterface cardProps : currentCards) {
      CardDisplay cardMock = new CardDisplay(String.valueOf(cardProps.getNum()),
          cardProps.getType(), cardProps.getMyColor());
      Node card = cardMock.getCard();
      cardDisplay.add(card);
      handList.getChildren().add(card);
    }
  }

  @Override
  public Node getDisplayableItem() {
    handList.setSpacing(20);
    return handList;
  }

  public String wildPopUp() {
    ChoiceDialog<String> dialog = new ChoiceDialog<>(WILDCOLORS[0], WILDCOLORS);
    dialog.setTitle("Wild Card Color");
    dialog.setHeaderText(null);
    dialog.setContentText("Choose the color you want to use:");
    dialog.getDialogPane().getButtonTypes().clear();
    ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(ok);
    dialog.showAndWait();

    return dialog.getSelectedItem();
  }

  public int selectCard() {
    ChoiceDialog<Integer> dialog = new ChoiceDialog<>(NUMBERS[0]);
    ObservableList<Integer> list = dialog.getItems();
    list.addAll(NUMBERS[1], NUMBERS[2], NUMBERS[3]);
    dialog.setTitle("Select Card");
    dialog.setHeaderText(null);
    dialog.setContentText("Choose the index of the card you want to play:");
    dialog.getDialogPane().getButtonTypes().clear();
    ButtonType draw = new ButtonType("Draw", ButtonBar.ButtonData.LEFT);
    ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(draw, ok);

    dialog.setResultConverter((ButtonType type) -> {
      ButtonBar.ButtonData data = type == null ? null : type.getButtonData();
      if (data == ButtonBar.ButtonData.LEFT) {
        return -1;
      }
      else {
        return null;
      }
    });

    Optional<Integer> result = dialog.showAndWait();

    if (result.get() == -1) {
      return result.get();
    }

    return dialog.getSelectedItem();
  }
}
