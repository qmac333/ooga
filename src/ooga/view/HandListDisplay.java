package ooga.view;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ooga.controller.UnoController;
import ooga.model.gameState.GameStateViewInterface;
import ooga.util.Config;

public class HandListDisplay implements DisplayableItem{

  private GameStateViewInterface gameState;
  private List<StackPane> cards;
  private UnoController controller;
  private HBox handList;

  private Timeline timeline;

  /**
   * Initialize a class that creates the display for an UNO hand.
   *
   * @param controller is a reference to the controller object to pass the consumer through to the
   *                   model
   */
  public HandListDisplay(UnoController controller) {
    this.controller = controller;
    gameState = controller.getGameState();
    handList = new HBox();
    handList.setAlignment(Pos.CENTER);

    timeline = new Timeline();
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(Config.REFRESH_RATE), e -> update()));
    timeline.play();

  }

  private void update() {
    try {
      handList.setSpacing(20);
      for (List<String> cardProps : gameState.getCurrentPlayerCards()) {
        StackPane stack = new StackPane();
        Rectangle card = new Rectangle(30, 25);
        Image image = new Image(new FileInputStream(String.format("./data/%s.png", cardProps.get(0))));
        ImageView imageView = new ImageView(image);
        stack.getChildren().addAll(card, imageView);
        StackPane.setMargin(stack, new Insets(0, 10, 0, 0));
        cards.add(stack);
        stack.setOnMouseClicked(e -> controller.playUserCard(cards.indexOf(stack)));
        handList.getChildren().add(stack);
      }
    } catch(Exception e) {
      System.out.println("Couldn't find the image file for the card.");
    }
  }

  @Override
  public Node getDisplayableItem() {
    return handList;
  }

//  /**
//   * Takes an update to the player's hand, and redraws the hand
//   *
//   * @param currentCards is a map with keys as card numbers, and values as groups of (color,
//   *                       label) for each of the cards of that number
//   */
//  @Override
//  public void accept(List<List<String>> currentCards) {
//  }
}
