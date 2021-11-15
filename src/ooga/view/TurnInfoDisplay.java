package ooga.view;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import ooga.controller.UnoController;
import ooga.model.GameStateViewInterface;
import ooga.util.TurnInfoChanges;

public class TurnInfoDisplay implements Consumer<TurnInfoChanges> {

  private GameStateViewInterface gameState;
  private Map<TurnInfoChanges, Consumer> changeHandlers;

  /**
   * Initialize a class that creates the display for the info on the current players and whose turn
   * it is.
   *
   * @param controller is a reference to the controller object to pass the consumer through to the
   *                   model
   */
  public TurnInfoDisplay(UnoController controller) {
    gameState = controller.getGameState();
    changeHandlers = new HashMap<TurnInfoChanges, Consumer>();
    createHandlerMap();


  }

  /**
   * Updates the player display based on a change in the model.
   *
   * @param changeType the type of the change in the model state.
   */
  @Override
  public void accept(TurnInfoChanges changeType) {

  }

  private void createHandlerMap() {
    changeHandlers.put(TurnInfoChanges.NUM_PLAYERS, e -> playersChangeHandler());
    changeHandlers.put(TurnInfoChanges.NUM_CARDS, e -> numCardsChangeHandler());
    changeHandlers.put(TurnInfoChanges.CURRENT_PLAYER, e -> currentPlayerChangeHandler());
    changeHandlers.put(TurnInfoChanges.DIRECTION, e -> newDirectionChangeHandler());
  }

  private void playersChangeHandler() {

  }

  private void numCardsChangeHandler() {

  }

  private void currentPlayerChangeHandler() {

  }

  private void newDirectionChangeHandler() {

  }


}
