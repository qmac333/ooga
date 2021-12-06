package ooga.view;

import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ooga.controller.UnoController;
import ooga.model.player.player.ViewPlayerInterface;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TurnInfoDisplayTest extends DukeApplicationTest {

  private UnoController controller;

  @Override
  public void start(Stage stage) {

    CardDisplay.initializeCards();
    controller = new UnoController(stage);
    controller.setLanguage("English");
    controller.createSplashScreen("English");
    controller.loadFile("data/configuration_files/Test Files/validNewFile1.json");
    Button playButton = lookup("#" + SplashScreen.PLAY_CSS_ID).query();
    clickOn(playButton);

  }

  @Test
  public void checkInitTable() {
    TableView<ViewPlayerInterface> playerTable = lookup("#" + TurnInfoDisplay.PLAYER_TABLE_CSS).query();
    List<ViewPlayerInterface> players = playerTable.getItems();
    assertEquals("Andrew", players.get(0).getName());
    assertEquals(7, players.get(0).getHandSize());
    assertEquals("Drew", players.get(1).getName());
    assertEquals(7, players.get(1).getHandSize());
    assertEquals("Quentin", players.get(2).getName());
    assertEquals(7, players.get(2).getHandSize());
  }

  @Test
  public void checkInitDirection() {
    ImageView arrow = lookup("#" + TurnInfoDisplay.ARROW_CSS).query();
    assertEquals(90, arrow.getRotate()); // arrow is pointing down
  }
}
