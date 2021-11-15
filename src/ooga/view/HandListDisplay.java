package ooga.view;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import ooga.controller.UnoController;

public class HandListDisplay implements Consumer<List<List<String>>> {

  private List<List<String>> currentCards;
  private List<StackPane> cards;
  private UnoController controller;

  /**
   * Initialize a class that creates the display for an UNO hand.
   *
   * @param controller is a reference to the controller object to pass the consumer through to the
   *                   model
   */
  public HandListDisplay(UnoController controller) {
    this.controller = controller;
  }

  public Node createHand() {
    HBox handList = new HBox();
    handList.setSpacing(20);
    try {
      for (List<String> cardProps : currentCards) {
        StackPane stack = new StackPane();
        Rectangle card = new Rectangle(30, 25);
        Image image = new Image(new FileInputStream(String.format("./data/%s.png", cardProps.get(0))));
        ImageView imageView = new ImageView(image);
        stack.getChildren().addAll(card, imageView);
        cards.add(stack);
        stack.setOnMouseClicked(e -> controller.playUserCard(cards.indexOf(stack)));
        handList.getChildren().add(stack);
      }
    } catch(Exception e) {

    }

    return handList;
  }

  /**
   * Takes an update to the player's hand, and redraws the hand
   *
   * @param currentCards is a map with keys as card numbers, and values as groups of (color,
   *                       label) for each of the cards of that number
   */
  @Override
  public void accept(List<List<String>> currentCards) {
    this.currentCards = currentCards;
  }
}
