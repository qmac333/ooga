package ooga.view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import ooga.controller.UnoDisplayController;
import ooga.model.cards.ViewCardInterface;
import ooga.model.gameState.GameStateViewInterface;

import java.util.List;


public class BlasterDisplay implements DisplayableItem {

    private GameStateViewInterface gameState;
    private UnoDisplayController controller;
    private FlowPane blasterCardsDisplay;
    private Pane displayableItem;
    private List<ViewCardInterface> blasterCards;

    /**
     * Initialize a class that creates the blaster.
     *
     * @param controller is a reference to the controller object to pass the consumer through to the
     *                   model
     */
    public BlasterDisplay(UnoDisplayController controller) {
        this.controller = controller;
        createBlasterCardsDisplay();
        displayableItem = new VBox();
        displayableItem.getChildren().add(blasterCardsDisplay);
        displayableItem.getStyleClass().add("hand_list_main_display");
        gameState = controller.getGameState();

        blasterCards = gameState.getBlasterCards();
    }

    public Node getDisplayableItem() {
        return displayableItem;
    }

    public void update() {
        blasterCardsDisplay.getChildren().clear();
        blasterCards = gameState.getBlasterCards();
        for (ViewCardInterface card : blasterCards) {
            CardDisplay cardMock;
            cardMock = new CardDisplay(String.valueOf(card.getNum()), card.getType(), card.getMyColor());
            VBox cardBox = new VBox();
            cardBox.getStyleClass().add("hand_list_card_box");

            Node cardDisplay = cardMock.getCard();
            cardBox.getChildren().add(cardDisplay);
            blasterCardsDisplay.getChildren().add(cardBox);
        }
    }

    private void createBlasterCardsDisplay() {
        blasterCardsDisplay = new FlowPane();
        blasterCardsDisplay.getStyleClass().add("blaster_card_flowpane");
    }
}
