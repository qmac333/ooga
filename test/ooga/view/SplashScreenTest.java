package ooga.view;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.controller.UnoController;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ResourceBundle;

public class SplashScreenTest extends DukeApplicationTest {

    private ResourceBundle cssIdResources;
    private ResourceBundle languageResources;
    private ResourceBundle modLanguageResources;
    private UnoController controller;

    @Override
    public void start(Stage stage) {
        cssIdResources = ResourceBundle.getBundle("ooga.resources.CSSId");
        languageResources = ResourceBundle.getBundle("ooga.resources.English");
        modLanguageResources = ResourceBundle.getBundle("ooga.resources.mods.English");
        controller = new UnoController(stage);
        controller.createSplashScreen("English");
    }

    @Test
    public void changeToDarkMode() {
        Button button = lookup("#" + cssIdResources.getString("DarkMode")).query();
        Paint lightModeTextColor = button.getTextFill();
        assertEquals(lightModeTextColor, Color.GHOSTWHITE);
        clickOn(button);
        Paint darkModeTextColor = button.getTextFill();
        assertEquals(darkModeTextColor, Color.BLACK);
        clickOn(button);
    }

    @Test
    public void changeToUncColors() {
        Button button = lookup("#" + cssIdResources.getString("UncColor")).query();
        Paint lightModeTextColor = button.getTextFill();
        assertEquals(lightModeTextColor, Color.GHOSTWHITE);
        clickOn(button);
        Paint uncTextColor = button.getTextFill();
        assertEquals(uncTextColor, Color.web("#000000ff"));
        clickOn(button);
    }

    @Test
    public void changeToSpringColors() {
        Button button = lookup("#" + cssIdResources.getString("SeasonColor")).query();
        Paint springTextColor = button.getTextFill();
        assertEquals(springTextColor, Color.GHOSTWHITE);
        clickOn(button);
        Paint fallTextColor = button.getTextFill();
        assertEquals(fallTextColor, Color.web("#f8f8ffff"));
        clickOn(button);
    }

    @Test
    public void enterGameParameters() {
        TextField points = lookup("#" + cssIdResources.getString("PointsText")).query();
        clickOn(points).write("400");

        ChoiceBox<String> gameType = lookup("#" + cssIdResources.getString("GameTypeChoice")).query();
        clickOn(gameType);
        select(gameType, languageResources.getString("Blast"));

        ChoiceBox<String> modType = lookup("#" + cssIdResources.getString("ModSelect")).query();
        clickOn(modType);
        select(modType, modLanguageResources.getString("Traditional"));

        TextField name1 = lookup("#" + cssIdResources.getString("NameText")).query();
        clickOn(name1).write("Quentin");

        ChoiceBox<String> playerType = lookup("#" + cssIdResources.getString("PlayerTypeInput")).query();
        clickOn(playerType);
        select(playerType, languageResources.getString("Human"));

        Button addPlayer = lookup("#" + cssIdResources.getString("AddPlayerToGame")).query();
        clickOn(addPlayer);

        clickOn(name1).write("Paul");
        clickOn(addPlayer);

        clickOn(name1).write("Andrew");
        clickOn(addPlayer);

        Button setGame = lookup("#" + cssIdResources.getString("SetGame")).query();
        clickOn(setGame);

        Text text = lookup("#" + cssIdResources.getString("NewGameText")).query();
        assertEquals(text.getText(), languageResources.getString("NewGame"));

        Button play = lookup("#" + cssIdResources.getString("PlayButton")).query();
        clickOn(play);
    }

    @Test
    public void loadFile() {
        Button setGame = lookup("#" + cssIdResources.getString("LoadNewGame")).query();
        clickOn(setGame);
        assertNull(controller.getUnoDisplay());
        assertNull(controller.getGameState());
    }

    @Test
    public void enterBadGameParameters() {
        TextField points = lookup("#" + cssIdResources.getString("PointsText")).query();
        clickOn(points).write("400");

        ChoiceBox<String> gameType = lookup("#" + cssIdResources.getString("GameTypeChoice")).query();
        clickOn(gameType);
        select(gameType, languageResources.getString("Blast"));

        ChoiceBox<String> modType = lookup("#" + cssIdResources.getString("ModSelect")).query();
        clickOn(modType);
        select(modType, modLanguageResources.getString("Traditional"));

        TextField name1 = lookup("#" + cssIdResources.getString("NameText")).query();
        clickOn(name1).write("Quentin");

        ChoiceBox<String> playerType = lookup("#" + cssIdResources.getString("PlayerTypeInput")).query();
        clickOn(playerType);
        select(playerType, languageResources.getString("Human"));

        Button setGame = lookup("#" + cssIdResources.getString("SetGame")).query();
        clickOn(setGame);

        Text text = lookup("#" + cssIdResources.getString("NewGameText")).query();
        assertNotEquals(text.getText(), languageResources.getString("NewGame"));
        assertEquals(text.getText(), "");
    }
}
