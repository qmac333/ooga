package ooga.view;

import java.util.function.Consumer;
import ooga.controller.UnoController;

public class TurnInfoDisplay implements Consumer<PlayersInfo> {

  /**
   * Initialize a class that creates the display for the info on the current players and whose turn it is.
   *
   * @param controller is a reference to the controller object to pass the consumer through to the
   *                   model
   */
  public TurnInfoDisplay(UnoController controller) {

  }

  /**
   * Updates the player display based on a change in the model.
   *
   * @param playersInfo contains information about the players in the game, whose turn it is, and the direction of play.
   */
  @Override
  public void accept(PlayersInfo playersInfo) {

  }
}
