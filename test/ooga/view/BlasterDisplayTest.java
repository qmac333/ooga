package ooga.view;

import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import ooga.controller.UnoController;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;


public class BlasterDisplayTest extends DukeApplicationTest {

    private ResourceBundle cssResources;
    private ResourceBundle cssIdResources = ResourceBundle.getBundle("ooga.resources.CSSId");

    @Override
    public void start(Stage stage) {
        cssResources = ResourceBundle.getBundle("ooga.resources.CSSId");
        UnoController controller = new UnoController(stage);
        controller.setLanguage("English");
        controller.createSplashScreen("English");
        controller.loadFile("data/configuration_files/Test Files/validNewFile4.json");
        Button playButton = lookup("#" + cssIdResources.getString("PlayButton")).query();
        clickOn(playButton);
    }

    @Test
    public void testBlasterGetsCard() {
        Button drawButton = lookup("#" + cssResources.getString("DrawButton")).query();
        clickOn(drawButton);

        FlowPane fp = lookup("#" + cssResources.getString("BlasterDisplay")).query();
        assertEquals(0, fp.getChildren().size());
    }
}
