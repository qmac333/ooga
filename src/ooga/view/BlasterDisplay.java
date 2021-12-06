package ooga.view;

import java.util.Collection;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import ooga.controller.interfaces.UnoDisplayController;
import ooga.model.cards.ViewCardInterface;
import ooga.model.gameState.GameStateViewInterface;

import java.util.ResourceBundle;


public class BlasterDisplay implements DisplayableItem {

    private GameStateViewInterface gameState;
    private UnoDisplayController controller;
    private FlowPane blasterCardsDisplay;
    private Pane displayableItem;
    private Collection<ViewCardInterface> blasterCards;

    private ResourceBundle languageResources;


    /**
     * Initialize a class that creates the blaster.
     *
     * @param controller is a reference to the controller object to pass the consumer through to the
     *                   model
     */
    public BlasterDisplay(UnoDisplayController controller, String language) {
        this.controller = controller;
        languageResources = ResourceBundle.getBundle(String.format("ooga.resources.%s", language));
        createBlasterCardsDisplay();
        initializeDisplayable();
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

    private void initializeDisplayable() {
        displayableItem = new VBox();
        Text blasterText = new Text(languageResources.getString("CardsBlaster"));
        blasterText.getStyleClass().add("blaster_text");
        displayableItem.getChildren().add(blasterText);
        displayableItem.getChildren().add(blasterCardsDisplay);
        displayableItem.getStyleClass().add("hand_list_main_display");
    }

    // displays alert/error message to the user
    private void blasted(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(languageResources.getString("OhNo"));
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }
}
