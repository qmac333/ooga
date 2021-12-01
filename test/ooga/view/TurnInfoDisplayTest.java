package ooga.view;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.controller.UnoController;
import ooga.util.Config;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TurnInfoDisplayTest extends DukeApplicationTest {

  private UnoController controller;

  @Override
  public void start(Stage stage) {

    CardDisplay.initializeCards();
    controller = new UnoController(stage);
    controller.createSplashScreen("English");
    controller.loadNewFile("data/configurationfiles/validNewFile1.json");
    Button playButton = lookup("#" + SplashScreen.PLAY_CSS_ID).query();
    clickOn(playButton);

  }

  @Test
  public void checkInitTable() {
    // TODO: need to figure out how to call render, even though it is a private method
//    TurnInfoDisplay display = new TurnInfoDisplay(controller);
//    // Initial table will be a 3 x 2 table
//
//    String[][] expected = {{"Andrew", "7"}, {"Drew", "7"}, {"Quentin", "7"}};
//
//
//    for (int i = 0; i < MockGameViewInterface.NUM_PLAYERS; i++) {
//      for (int j = 0; j < 2; j++) {
//        Text text = (Text) display.getTable().getCell(j, i);
//        assertEquals(expected[i][j], text.getText());
//      }
//    }
  }

  @Test
  public void checkInitDirection() {
    ImageView arrow = lookup("#" + TurnInfoDisplay.ARROW_CSS).query();
    assertEquals(90, arrow.getRotate()); // arrow is pointing down
  }

  @Test
  public void reverseDirection() {
    // TODO: need to figure out how to call render()
//    controller.getModel().reverseGamePlay();
//    ImageView arrow = lookup("#" + TurnInfoDisplay.ARROW_CSS).query();
//    assertEquals(-90, arrow.getRotate()); // arrow is pointing down
  }

  private int playCard() {
    return 0;
  }

  private String sendColor() {
    return "red";
  }


}
