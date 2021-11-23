package ooga.view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import ooga.controller.UnoDisplayController;
import ooga.model.cards.ViewCardInterface;
import ooga.util.Config;

/**
 * Class for displaying the deck and discard pile.
 */
public class DeckDisplay implements DisplayableItem {

  private UnoDisplayController controller;
  private Pane displayedItem;
  private Timeline updateTimeline;

  public DeckDisplay(UnoDisplayController controller) {
    this.controller = controller;
    displayedItem = new VBox();
    updateTimeline = new Timeline();
    updateTimeline.setCycleCount(Animation.INDEFINITE);
    updateTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(Config.REFRESH_RATE), e -> update()));
    updateTimeline.play();
  }

  @Override
  public Node getDisplayableItem() {
    return displayedItem;
  }

  private void update() {
    displayedItem.getChildren().clear();

    CardDisplay topDeck = new CardDisplay();
    ViewCardInterface topDiscard = controller.getGameState().getLastCardThrown();
    // return a displayable instance of that card, add to the display
    CardDisplay card = new CardDisplay(String.valueOf(topDiscard.getNum()), topDiscard.getType(), topDiscard.getMyColor());
    displayedItem.getChildren().add(topDeck.getCard());
    displayedItem.getChildren().add(card.getCard());
  }
}
