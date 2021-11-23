package ooga.view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ooga.controller.UnoDisplayController;
import ooga.model.cards.ViewCardInterface;
import ooga.util.Config;

/**
 * Class for displaying the deck and discard pile.
 */
public class DeckDisplay implements DisplayableItem {

  private UnoDisplayController controller;
  private HBox displayedItem;
  private Timeline updateTimeline;

  private VBox deckDisplay;
  private VBox discardPileDisplay;

  public DeckDisplay(UnoDisplayController controller) {
    this.controller = controller;
    displayedItem = new HBox();
    displayedItem.setAlignment(Pos.CENTER);
    displayedItem.setSpacing(20);

    initDeckDisplay();
    initDiscardPileDisplay();
    displayedItem.getChildren().addAll(deckDisplay, discardPileDisplay);

    updateTimeline = new Timeline();
    updateTimeline.setCycleCount(Animation.INDEFINITE);
    updateTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(Config.REFRESH_RATE), e -> update()));
    updateTimeline.play();
  }

  @Override
  public Node getDisplayableItem() {
    return displayedItem;
  }

  private void initDeckDisplay() {
    deckDisplay = new VBox();
    deckDisplay.setAlignment(Pos.CENTER);
    deckDisplay.getChildren().addAll(new CardDisplay().getCard(), new Text(String.valueOf(controller.getGameState().getDeck().getNumCards())));
  }

  private void initDiscardPileDisplay() {
    discardPileDisplay = new VBox();
  }

  private void update() {
    updateDeckDisplay();
    updateDiscardPileDisplay();
  }

  private void updateDeckDisplay() {
    int topIndex = deckDisplay.getChildren().size() - 1;
    deckDisplay.getChildren().remove(topIndex);
    deckDisplay.getChildren().add(new Text(String.valueOf(controller.getGameState().getDeck().getNumCards())));
     //TODO: handle update for when deck is out of cards
  }

  private void updateDiscardPileDisplay() {
    discardPileDisplay.getChildren().clear();
    ViewCardInterface topDiscard = controller.getGameState().getDiscardPile().lastCardPushed();
    // return a displayable instance of that card, add to the display
    CardDisplay card = new CardDisplay(String.valueOf(topDiscard.getNum()), topDiscard.getType(), topDiscard.getMyColor());
    discardPileDisplay.getChildren().add(card.getCard());
  }
}
