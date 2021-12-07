package ooga.view.subdisplays;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CardDisplay {

  // variable only used in deprecated method
  private static final ResourceBundle imageResources = ResourceBundle.getBundle("ooga.resources.mods.Traditional");

  public static Map<String, Image> IMAGES;

  private static final String LOGO = "Logo";

  private static final Map<String, Color> COLORS = Map.of(
      "Blue", Color.BLUE,
      "Red", Color.RED,
      "Green", Color.GREEN,
      "Yellow", Color.YELLOW
  );

  private static Color DEFAULT_COLOR = Color.BLACK;
  private static double CARD_WIDTH = 60;
  private static double CARD_HEIGHT = 100;
  private static double CARD_OFFSET = 10;

  private StackPane cardDisplay;

  /**
   * Creates a display for the top side of a card.
   */
  public CardDisplay() {
    this(String.valueOf(-1), "Top", "Black", true, false);

  }

  public CardDisplay(String number, String type, String color, boolean humanPlayer, boolean valid) {
    cardDisplay = new StackPane();
    Rectangle base = new Rectangle(CARD_WIDTH + CARD_OFFSET, CARD_HEIGHT + CARD_OFFSET);
    base.setFill(DEFAULT_COLOR);
    Rectangle card = new Rectangle(CARD_WIDTH, CARD_HEIGHT);
    if (humanPlayer) {
      card.setFill(COLORS.get(color));
    }
    else  {
      card.setFill(DEFAULT_COLOR);
    }
    Rectangle highlight = new Rectangle(CARD_WIDTH + 3*CARD_OFFSET, CARD_HEIGHT + 3*CARD_OFFSET);
    highlight.setFill(Color.YELLOW);
    highlight.opacityProperty().set(0.5);

    String loadFileString;
    if (!humanPlayer) {
      loadFileString = LOGO;
    } else if (type.equals("Number")) {
      loadFileString = String.valueOf(number);
    } else {
      loadFileString = type;
    }

    Image image = IMAGES.get(loadFileString);
    ImageView imageView = new ImageView(image);
    imageView.setFitHeight(CARD_WIDTH);
    imageView.setFitWidth(CARD_HEIGHT / 2);

    cardDisplay.getChildren().addAll(base, card, imageView);
    if (humanPlayer && valid) {
      cardDisplay.getChildren().add(highlight);
    }
  }

  public CardDisplay(String number, String type, String color) {
    cardDisplay = new StackPane();
    Rectangle base = new Rectangle(CARD_WIDTH + CARD_OFFSET, CARD_HEIGHT + CARD_OFFSET);
    base.setFill(DEFAULT_COLOR);
    Rectangle card = new Rectangle(CARD_WIDTH, CARD_HEIGHT);
    card.setFill(COLORS.get(color));
    Rectangle highlight = new Rectangle(CARD_WIDTH + 3*CARD_OFFSET, CARD_HEIGHT + 3*CARD_OFFSET);
    highlight.setFill(Color.YELLOW);
    highlight.opacityProperty().set(0.5);

    String loadFileString;
    if (type.equals("Number")) {
      loadFileString = String.valueOf(number);
    } else {
      loadFileString = type;
    }

    Image image = IMAGES.get(loadFileString);
    ImageView imageView = new ImageView(image);
    imageView.setFitHeight(CARD_WIDTH);
    imageView.setFitWidth(CARD_HEIGHT / 2);

    cardDisplay.getChildren().addAll(base, card, imageView);
  }

  public Parent getCard() {
    return cardDisplay;
  }


  @Deprecated
  public static void initializeCards() {
    try {
      IMAGES = new HashMap<>();
      for (String key : imageResources.keySet()) {
        IMAGES.put(key, new Image(new FileInputStream(imageResources.getString(key))));
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void initializeCards(ResourceBundle resources) {
    try {
      IMAGES = new HashMap<>();
      for (String key : resources.keySet()) {
        IMAGES.put(key, new Image(new FileInputStream(resources.getString(key))));
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

}
