package ooga.view.subdisplays;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ooga.controller.interfaces.UnoDisplayController;
import ooga.model.cards.ViewCardInterface;

import java.util.ResourceBundle;

/**
 * Class for displaying the deck and discard pile.
 */
public class DeckDisplay implements DisplayableItem {

  private UnoDisplayController controller;
  private HBox displayedItem;

  private ResourceBundle languageResources;
  private ResourceBundle cssIdResources;

  private VBox deckDisplay;
  private VBox discardPileDisplay;

  // discard pile display
  private Text colorText;

  // deck display
  private Text numCardsDeckText;

  public DeckDisplay(UnoDisplayController controller, String language) {
    this.controller = controller;
    languageResources = ResourceBundle.getBundle(String.format("ooga.resources.%s", language));
    cssIdResources = ResourceBundle.getBundle("ooga.resources.CSSId");
    displayedItem = new HBox();
    displayedItem.getStyleClass().add("deck_main_display");

    initDeckDisplay();
    initDiscardPileDisplay();
    displayedItem.getChildren().addAll(deckDisplay, discardPileDisplay);

  }

  @Override
  public Node getDisplayableItem() {
    return displayedItem;
  }

  private void initDeckDisplay() {
    deckDisplay = new VBox();
    deckDisplay.getStyleClass().add("vbox");

    numCardsDeckText = new Text();
    numCardsDeckText.getStyleClass().add("text");

    numCardsDeckText.setId(cssIdResources.getString("NumCardsDeck"));
    updateNumCardsDeck();

    // add a blank card to the display
    Parent cardDisplay = new CardDisplay().getCard();
    cardDisplay.setId(cssIdResources.getString("DeckCard"));
    deckDisplay.getChildren().addAll(cardDisplay, numCardsDeckText);
  }

  private void initDiscardPileDisplay() {
    discardPileDisplay = new VBox();
    discardPileDisplay.getStyleClass().add("vbox");
    colorText = new Text();
    colorText.getStyleClass().add("text");
  }

  public void update() {
    updateDeckDisplay();
    updateDiscardPileDisplay();
  }

  private void updateDeckDisplay() {
    updateNumCardsDeck();
    //TODO: handle update for when deck is out of cards
  }

  private void updateDiscardPileDisplay() {
    discardPileDisplay.getChildren().clear();
    ViewCardInterface topDiscard = (ViewCardInterface) controller.getGameState().getDiscardPile().lastCardPushed();
    // return a displayable instance of that card, add to the display
    CardDisplay card = new CardDisplay(String.valueOf(topDiscard.getNum()), topDiscard.getType(),
        topDiscard.getMyColor());
    card.getCard().setId(cssIdResources.getString("DiscardCard"));
    colorText.setText(String.format("%s %s", languageResources.getString("CardColor"),
            topDiscard.getMyColor().toUpperCase()));
    discardPileDisplay.getChildren().addAll(card.getCard(), colorText);
  }

  private void updateNumCardsDeck() {
    numCardsDeckText.setText(String.valueOf(controller.getGameState().getDeck().getNumCards()));
  }
}
