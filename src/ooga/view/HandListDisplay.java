package ooga.view;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ooga.controller.UnoDisplayController;
import ooga.model.cards.ViewCardInterface;
import ooga.model.gameState.GameStateViewInterface;

public class HandListDisplay implements DisplayableItem {

  private static final String[] WILDCOLORS = {"Red", "Blue", "Green", "Yellow"};

  private ResourceBundle languageResources;

  private GameStateViewInterface gameState;
  private UnoDisplayController controller;
  private FlowPane handList;
  private List<ViewCardInterface> currentCards;


  /**
   * Initialize a class that creates the display for an UNO hand.
   *
   * @param controller is a reference to the controller object to pass the consumer through to the
   *                   model
   */
  public HandListDisplay(UnoDisplayController controller, String language) {
    this.controller = controller;
    languageResources = ResourceBundle.getBundle(String.format("ooga.resources.%s", language));
    gameState = controller.getGameState();
    handList = new FlowPane();
    handList.setAlignment(Pos.CENTER);

    currentCards = gameState.getCurrentPlayerCards();

  }

  public void update() {
    int indexCounter = 0;
    handList.getChildren().clear();
    currentCards = gameState.getCurrentPlayerCards();
    Collection<Integer> validCards = gameState.getValidIndexes();
    for (int i=0; i< currentCards.size(); i++) {
      CardDisplay cardMock;;
      if (validCards.contains(i)) {
        cardMock = new CardDisplay(String.valueOf(currentCards.get(i).getNum()),
                currentCards.get(i).getType(), currentCards.get(i).getMyColor(), true);
      }
      else {
        cardMock = new CardDisplay(String.valueOf(currentCards.get(i).getNum()),
                currentCards.get(i).getType(), currentCards.get(i).getMyColor(), false);
      }
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
    handList.setHgap(20);
    handList.setVgap(20);
    return handList;
  }

  public String wildPopUp() {
    ChoiceDialog<String> dialog = new ChoiceDialog<>(WILDCOLORS[0], WILDCOLORS);
    dialog.setTitle(languageResources.getString("WildCardColor"));
    dialog.setHeaderText(null);
    dialog.setContentText(languageResources.getString("ChooseColor"));
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
    dialog.setTitle(languageResources.getString("SelectCard"));
    dialog.setHeaderText(null);
    dialog.setContentText(languageResources.getString("ChooseCard"));
    dialog.getDialogPane().getButtonTypes().clear();
    ButtonType draw = new ButtonType(languageResources.getString("Draw"), ButtonBar.ButtonData.LEFT);
    ButtonType playUno = new ButtonType(languageResources.getString("PlayAndUno"), ButtonBar.ButtonData.FINISH);
    ButtonType play = new ButtonType(languageResources.getString("PlayCard"), ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(draw, playUno, play);

    dialog.setResultConverter((ButtonType type) -> {
      ButtonBar.ButtonData data = type == null ? null : type.getButtonData();
      if (data == ButtonBar.ButtonData.LEFT) {
        return -1;
      }
      else if (data == ButtonBar.ButtonData.OK_DONE) {
        return dialog.getSelectedItem();
      }
      else if (data == ButtonBar.ButtonData.FINISH) {
        controller.getGameState().setCalledUno(true);
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
