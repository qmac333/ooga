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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ooga.controller.UnoDisplayController;
import ooga.model.cards.ViewCardInterface;
import ooga.model.gameState.GameStateViewInterface;
import ooga.util.Config;

public class HandListDisplay implements DisplayableItem {

  private static final String[] WILDCOLORS = {"Red", "Blue", "Green", "Yellow"};

  private GameStateViewInterface gameState;
  private UnoDisplayController controller;
  private HBox handList;
  private List<ViewCardInterface> currentCards;


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

    currentCards = gameState.getCurrentPlayerCards();

  }

  public void update() {
    int indexCounter = 0;
    handList.getChildren().clear();
    currentCards = gameState.getCurrentPlayerCards();
    for (ViewCardInterface cardProps : currentCards) {
      CardDisplay cardMock = new CardDisplay(String.valueOf(cardProps.getNum()),
          cardProps.getType(), cardProps.getMyColor());
      VBox cardBox = new VBox();
      cardBox.getStyleClass().add("hand_list_card_box");

      Node card = cardMock.getCard();
      Text index = new Text(String.valueOf(indexCounter));
      index.getStyleClass().add("text");
      cardBox.getChildren().addAll(index, card);
      handList.getChildren().add(cardBox);
      indexCounter++;
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
    int numCards = gameState.getCurrentPlayerCards().size();
    if (numCards == 0) {
      return -1; // TODO: determine what happens in model when a player wins
    }

    ChoiceDialog<Integer> dialog = new ChoiceDialog<>(0); // default to first card in hand
    ObservableList<Integer> list = dialog.getItems();
    for (int i = 0; i < numCards; i++) {
      list.add(i);
    }
    dialog.setTitle("Select Card");
    dialog.setHeaderText(null);
    dialog.setContentText("Choose the index of the card you want to play.\nThe leftmost card is at index 0, and the index of each card to the right goes up by 1.");
    dialog.getDialogPane().getButtonTypes().clear();
    ButtonType draw = new ButtonType("Draw", ButtonBar.ButtonData.LEFT);
    ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(draw, ok);

    dialog.setResultConverter((ButtonType type) -> {
      ButtonBar.ButtonData data = type == null ? null : type.getButtonData();
      if (data == ButtonBar.ButtonData.LEFT) {
        return -1;
      }
      else if (data == ButtonBar.ButtonData.OK_DONE) {
        return dialog.getSelectedItem();
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
