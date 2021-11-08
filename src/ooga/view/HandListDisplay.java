package ooga.view;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import ooga.controller.UnoController;

public class HandListDisplay implements Consumer<Map<Integer, List<List<String>>>> {

  /**
   * Initialize a class that creates the display for an UNO hand.
   *
   * @param controller is a reference to the controller object to pass the consumer through to the
   *                   model
   */
  public HandListDisplay(UnoController controller) {

  }

  /**
   * Takes an update to the player's hand, and redraws the hand
   *
   * @param integerListMap is a map with keys as card numbers, and values as groups of (color,
   *                       label) for each of the cards of that number
   */
  @Override
  public void accept(Map<Integer, List<List<String>>> integerListMap) {

  }
}
