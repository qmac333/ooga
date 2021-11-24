package ooga.view;

import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.controller.UnoController;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TurnInfoDisplayTest extends DukeApplicationTest {

  private TurnInfoDisplay display;
  private UnoController controller;

  @Override
  public void start(Stage stage) {
    CardDisplay.initializeCards();
    controller = new UnoController(stage);
    controller.languageHandler("English");
    controller.loadNewHandler("data/configurationfiles/example1.json");

    controller.getGameState().createDeck(Map.of("DrawFour", () -> sendColor(), "Wild", () -> sendColor()));
    // send suppliers down to the model
    try {
      controller.getGameState().createPlayers(() -> playCard());
    } catch (Exception e) {
      e.getMessage();
    }

    display = new TurnInfoDisplay(controller);
  }

  @Test
  public void checkInitTable() {
    // Initial table will be a 3 x 2 table

    String[][] expected = {{"Andrew", "7"}, {"Drew", "7"}, {"Quentin", "7"}};

    // pause, wait for the model to update the display
    pause(1000);

    for (int i = 0; i < MockGameViewInterface.NUM_PLAYERS; i++) {
      for (int j = 0; j < 2; j++) {
        // now check the contents of the table
        //Text text = lookup(String.format("#TurnInfo_%d_%d", j, i)).query();
        Text text = (Text) display.getTable().getCell(j, i);
        assertEquals(expected[i][j], text.getText());
      }
    }
  }

  private void pause(double millis) {
    long init = System.currentTimeMillis();
    while (System.currentTimeMillis() < init + millis) {
      // spin
    }
  }

  private int playCard() {
    return 0;
  }

  private String sendColor() {
    return "red";
  }



}
