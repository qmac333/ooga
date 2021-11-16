package ooga.view;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Dictionary;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ooga.controller.UnoController;
import ooga.controller.UnoDisplayController;
import ooga.model.gameState.GameStateViewInterface;
import ooga.util.Config;

public class HandListDisplay implements DisplayableItem {

  private static final Map<String, Color> colors = Map.of(
          "blue", Color.BLUE,
          "red", Color.RED,
          "green", Color.GREEN,
          "yellow", Color.YELLOW
  );

  private GameStateViewInterface gameState;
  private UnoDisplayController controller;
  private HBox handList;
  private List<List<String>> currentCards;
  private List<StackPane> cardDisplay;
  private Timeline timeline;

  /**
   * Initialize a class that creates the display for an UNO hand.
   *
   * @param controller is a reference to the controller object to pass the consumer through to the
   *                   model
   */
  public HandListDisplay(UnoDisplayController controller) {
    this.controller = controller;
    gameState = controller.getGameState();
    handList = new HBox();
    handList.setAlignment(Pos.CENTER);

    cardDisplay = new ArrayList<>();
    currentCards = gameState.getCurrentPlayerCards();

    timeline = new Timeline();
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(Config.REFRESH_RATE), e -> update()));
    timeline.play();
  }

  private void update() {
    currentCards = gameState.getCurrentPlayerCards();
  }

  @Override
  public Node getDisplayableItem() {
    try {
      handList.setSpacing(20);
      for (List<String> cardProps : currentCards) {
        StackPane stack = new StackPane();
        Rectangle base = new Rectangle(70, 110);
        base.setFill(Color.BLACK);
        Rectangle card = new Rectangle(60, 100);
        card.setFill(colors.get(cardProps.get(1)));

        Image image = new Image(new FileInputStream(String.format("./data/%s.png", cardProps.get(0))));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(60);
        imageView.setFitWidth(50);
        stack.getChildren().addAll(base, card, imageView);
        StackPane.setMargin(stack, new Insets(0, 10, 0, 0));
        cardDisplay.add(stack);
        stack.setOnMouseClicked(e -> controller.playUserCard(cardDisplay.indexOf(stack)));
        handList.getChildren().add(stack);
      }
    }
    catch(Exception e) {
      System.out.println("Couldn't find the image file for the card.");
    }
    return handList;
  }
}
