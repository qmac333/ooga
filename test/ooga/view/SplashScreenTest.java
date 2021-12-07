package ooga.view;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import ooga.controller.UnoController;
import ooga.view.gamescreens.SplashScreen;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.ResourceBundle;

public class SplashScreenTest extends DukeApplicationTest {

    private ResourceBundle cssIdResources = ResourceBundle.getBundle("ooga.resources.CSSId");

    @Override
    public void start(Stage stage) {
        UnoController controller = new UnoController(stage);
        controller.createSplashScreen("English");
    }

    @Test
    public void playBeforeLoad() {
        Button button = lookup("#" + cssIdResources.getString("PlayButton")).query();
        clickOn(button);
    }

    @Test
    public void loadFile() {
        Button button = lookup("#" + cssIdResources.getString("LoadNewGame")).query();
        clickOn(button);
    }
}
