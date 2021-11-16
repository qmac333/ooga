package ooga.view;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import ooga.controller.UnoController;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class SplashScreenTest extends DukeApplicationTest {

    @Override
    public void start(Stage stage) {
        UnoController controller = new UnoController(stage);
        controller.start();
    }

    @Test
    public void playBeforeLoad() {
        Button button = lookup("Play").query();
        clickOn(button);
    }

    @Test
    public void loadFile() {
        Button button = lookup("Load New Game").query();
        clickOn(button);
    }
}
