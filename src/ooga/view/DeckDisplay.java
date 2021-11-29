package ooga.view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
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

  public static final String DECK_NUM_CARDS_TEXT_CSS = "NumCardsDeck";
  public static final String DECK_CARD_CSS = "DeckCard";
  public static final String DISCARD_PILE_CSS = "DiscardCard";
  private UnoDisplayController controller;
  private HBox displayedItem;
  private Timeline updateTimeline;

  private VBox deckDisplay;
  private VBox discardPileDisplay;

  // deck display
  private Text numCardsDeckText;

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
    updateTimeline.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(Config.REFRESH_RATE), e -> update()));
    updateTimeline.play();
  }

  @Override
  public Node getDisplayableItem() {
    return displayedItem;
  }

  private void initDeckDisplay() {
    deckDisplay = new VBox();
    deckDisplay.setAlignment(Pos.CENTER);

    numCardsDeckText = new Text();
    numCardsDeckText.setId(DECK_NUM_CARDS_TEXT_CSS);
    updateNumCardsDeck();

    // add a blank card to the display
    Parent cardDisplay = new CardDisplay().getCard();
    cardDisplay.setId(DECK_CARD_CSS);
    deckDisplay.getChildren().addAll(cardDisplay, numCardsDeckText);
  }

  private void initDiscardPileDisplay() {
    discardPileDisplay = new VBox();
  }

  private void update() {
    updateDeckDisplay();
    updateDiscardPileDisplay();
  }

  private void updateDeckDisplay() {
    updateNumCardsDeck();
    //TODO: handle update for when deck is out of cards
  }

  private void updateDiscardPileDisplay() {
    discardPileDisplay.getChildren().clear();
    ViewCardInterface topDiscard = controller.getGameState().getDiscardPile().lastCardPushed();
    // return a displayable instance of that card, add to the display
    CardDisplay card = new CardDisplay(String.valueOf(topDiscard.getNum()), topDiscard.getType(),
        topDiscard.getMyColor());
    card.getCard().setId(DISCARD_PILE_CSS);

    discardPileDisplay.getChildren().add(card.getCard());
  }

  private void updateNumCardsDeck() {
    numCardsDeckText.setText(String.valueOf(controller.getGameState().getDeck().getNumCards()));
  }
}
