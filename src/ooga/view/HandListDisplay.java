package ooga.view;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Collection;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ooga.controller.UnoDisplayController;
import ooga.model.cards.ViewCardInterface;
import ooga.model.gameState.GameStateViewInterface;

public class HandListDisplay implements DisplayableItem {

  private static final String[] WILDCOLORS = {"Red", "Blue", "Green", "Yellow"};

  private ResourceBundle languageResources;

  private GameStateViewInterface gameState;
  private UnoDisplayController controller;
  private Pane displayableItem;
  private Pane buttonPanel;
  private FlowPane handList;
  private List<ViewCardInterface> currentCards;

  private Button drawButton;

  private Runnable endTurn;
  private int selectedIndex;


  /**
   * Initialize a class that creates the display for an UNO hand.
   *
   * @param controller is a reference to the controller object to pass the consumer through to the
   *                   model
   */
  public HandListDisplay(UnoDisplayController controller, Runnable endTurn, String language) {
    this.controller = controller;
    this.endTurn = endTurn;
    languageResources = ResourceBundle.getBundle(String.format("ooga.resources.%s", language));
    gameState = controller.getGameState();
    createHandPanel();
    createButtonPanel();
    displayableItem = new VBox();
    displayableItem.getStyleClass().add("hand_list_main_display");
    displayableItem.getChildren().add(handList);

    currentCards = gameState.getCurrentPlayerCards();
    selectedIndex = 0;

  }

  private void createHandPanel() {
    handList = new FlowPane();
    handList.setAlignment(Pos.CENTER);
    handList.setHgap(20);
    handList.setVgap(20);
  }

  private void createButtonPanel() {
    buttonPanel = new HBox();
    buttonPanel.getStyleClass().add("hand_list_button_panel");

    drawButton = new Button(languageResources.getString("Draw"));
    drawButton.setOnMouseClicked(e -> playTurn(-1));
    drawButton.getStyleClass().add("hand_list_button");

    Button unoButton = new Button(languageResources.getString("CallUno"));
    //unoButton.setOnMouseClicked(e -> controller.getGameState().setCalledUno(true));
    unoButton.getStyleClass().add("hand_list_button");

    buttonPanel.getChildren().addAll(drawButton, unoButton);
  }

  public void update() {
    handList.getChildren().clear();
    currentCards = gameState.getCurrentPlayerCards();
    Collection<Integer> validCards = gameState.getValidIndexes();
    for (int i=0; i< currentCards.size(); i++) {
      CardDisplay cardMock;
      if (validCards.contains(i) && gameState.userPicksCard()) {
        cardMock = new CardDisplay(String.valueOf(currentCards.get(i).getNum()),
                currentCards.get(i).getType(), currentCards.get(i).getMyColor(), gameState.userPicksCard(), true);
      }
      else {
        cardMock = new CardDisplay(String.valueOf(currentCards.get(i).getNum()),
                currentCards.get(i).getType(), currentCards.get(i).getMyColor(), gameState.userPicksCard(), false);
      }
      VBox cardBox = new VBox();
      cardBox.getStyleClass().add("hand_list_card_box");

      Node card = cardMock.getCard();
      cardBox.getChildren().add(card);
      int cardIndex = i;

      if (gameState.userPicksCard()) {
        if (validCards.contains(cardIndex)) {
          cardBox.setOnMousePressed(e -> {
            playTurn(cardIndex);
          });
        }
        else {
          cardBox.setOnMousePressed(e -> showError(languageResources.getString("InvalidCardClicked")));
        }
      }

      handList.getChildren().add(cardBox);
    }

    if (displayableItem.getChildren().size() > 1) {
      displayableItem.getChildren().remove(0);
    }

    if (gameState.userPicksCard()) {
      displayableItem.getChildren().add(0, buttonPanel);
      drawButton.getStyleClass().clear();
      if (gameState.getValidIndexes().size() == 0) {
        drawButton.getStyleClass().add("hand_list_button_draw_required");
      }
      else {
        drawButton.getStyleClass().add("hand_list_button");
      }
    }

  }

  @Override
  public Node getDisplayableItem() {
    return displayableItem;
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
    return selectedIndex;
  }

  private void playTurn(int index) {
    selectedIndex = index;
    gameState.playTurn();
    endTurn.run(); // update the rest of the display
  }

  // displays alert/error message to the user
  private void showError(String alertMessage) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setContentText(alertMessage);
    alert.show();
  }

}
