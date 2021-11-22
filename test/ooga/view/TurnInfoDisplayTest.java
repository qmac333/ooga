package ooga.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TurnInfoDisplayTest extends DukeApplicationTest {

  private TurnInfoDisplay display;
  private MockController controller;

  @Override
  public void start(Stage stage) {
    CardDisplay.initializeCards();
    controller = new MockController();
    display = new TurnInfoDisplay(controller);
    Group root = new Group();
    root.getChildren().add(display.getDisplayableItem());
    Scene scene = new Scene(root, 500, 500);
    stage.setScene(scene);
  }

  @Test
  public void checkInitTable() {
    // Initial table will be a 3 x 2 table
    // Should look like this:
    // Quentin, 1
    // Andrew, 2
    // Will, 3

    String[][] expected = {{"Quentin", "1"}, {"Andrew", "2"}, {"Will", "3"}};

    // pause, wait for the model to update the display
    pause(1000);

    for (int i = 0; i < MockGameViewInterface.NUM_PLAYERS; i++) {
      for (int j = 0; j < 2; j++) {
        // now check the contents of the table
        //Text text = lookup(String.format("#TurnInfo_%d_%d", j, i)).query();
        Text text = (Text)display.getTable().getCell(j, i);
        assertEquals(expected[i][j], text.getText());
      }
    }
  }

  @Test
  public void addPlayer() {
    // update the model
    controller.addPlayer();
    pause(1000);
    assertEquals(MockGameViewInterface.NUM_PLAYERS + 1, display.getTable().getNumRows());
    assertEquals("Drew", ((Text)display.getTable().getCell(0, MockGameViewInterface.NUM_PLAYERS)).getText());
  }

  private void pause(double millis) {
    long init = System.currentTimeMillis();
    while (System.currentTimeMillis() < init + millis) {
      // spin
    }
  }










}
