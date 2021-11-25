package ooga.view;

import java.util.ArrayList;
import java.util.List;

import java.util.function.Consumer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

  private GameStateViewInterface gameState;
  private UnoDisplayController controller;
  private HBox handList;
  private List<ViewCardInterface> currentCards;
  private List<Node> cardDisplay;
  private Timeline timeline;

  private Consumer<Integer> playCard;

  /**
   * Initialize a class that creates the display for an UNO hand.
   *
   * @param controller is a reference to the controller object to pass the consumer through to the
   *                   model
   */
  public HandListDisplay(UnoDisplayController controller, Consumer<Integer> selectCard) {
    this.controller = controller;
    gameState = controller.getGameState();
    handList = new HBox();
    handList.setAlignment(Pos.CENTER);

    cardDisplay = new ArrayList<>();
    currentCards = gameState.getCurrentPlayerCards();

    playCard = selectCard;

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

      card.setOnMousePressed(e -> {
        if (cardProps.getType().equals("Wild") || cardProps.getType().equals("DrawFour")) {
          wildPopUp();
        }
        playCard.accept(cardDisplay.indexOf(card));
      });
    }
  }

  @Override
  public Node getDisplayableItem() {
    handList.setSpacing(20);
    return handList;
  }

  private void wildPopUp() {
    ChoiceDialog<String> dialog = new ChoiceDialog<>(
            WILDCOLORS[0], WILDCOLORS);
    dialog.setTitle("Wild Card Color");
    dialog.setHeaderText(null);
    dialog.setContentText("Choose the color you want to use:");
    dialog.getDialogPane().getButtonTypes().clear();
    ButtonType color = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().add(color);
    dialog.showAndWait();
  }
}
