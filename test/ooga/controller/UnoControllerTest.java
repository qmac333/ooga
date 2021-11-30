package ooga.controller;

import static org.junit.jupiter.api.Assertions.*;

import javafx.stage.Stage;
import ooga.view.CardDisplay;
import ooga.model.gameState.GameState;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class UnoControllerTest extends DukeApplicationTest {
    private UnoController controller;
    private static final String VALID_NEW_FILE_1_PATH = Paths.get(".").toAbsolutePath().normalize() + "/data/configurationfiles/validNewFile1.json";
    private static final String VALID_NEW_FILE_2_PATH = Paths.get(".").toAbsolutePath().normalize() + "/data/configurationfiles/validNewFile2.json";
    private static final String INVALID_NEW_FILE_1_PATH = Paths.get(".").toAbsolutePath().normalize() + "/data/configurationfiles/invalidNewFile1.json";

    @Override
    public void start(Stage stage){
        controller = new UnoController(stage);
    }

    @Test
    void creatingInitialMenuScreen(){
        runAsJFXAction(() -> controller.start());
        assertNotNull(controller.getLanguageScreen());
    }

    @Test
    void creatingSplashScreen(){
        runAsJFXAction(() -> controller.createSplashScreen("English"));
        assertNotNull(controller.getSplashScreen());
    }

    @Test
    void pressingBackButton(){
        runAsJFXAction(() -> controller.backButtonHandler());
        assertNotNull(controller.getLanguageScreen());
    }

    @Test
    void loadingNewFile(){
        assertTrue(controller.loadNewFile(VALID_NEW_FILE_1_PATH));
    }

    @Test
    void loadingNewInvalidFile(){
        assertFalse(controller.loadNewFile(INVALID_NEW_FILE_1_PATH));
    }

    @Test
    void checkingModelObjectAfterLoadingNewFile(){
        assertTrue(controller.loadNewFile(VALID_NEW_FILE_1_PATH));
        String version = "Basic";
        Map<String, String> playerMap = new HashMap<>();
        playerMap.put("Andrew", "Human");
        playerMap.put("Drew", "CPU");
        playerMap.put("Quentin", "Human");
        int pointsToWin = 500;
        boolean stackable = true;
        GameState expected = new GameState(version, playerMap, pointsToWin, stackable);
        assertTrue(expected.compareInitialParameters(controller.getModel()));
    }

    @Test
    void checkingModelObjectAfterLoadingTwoNewFilesInARow(){
        assertTrue(controller.loadNewFile(VALID_NEW_FILE_1_PATH));
        assertTrue(controller.loadNewFile(VALID_NEW_FILE_2_PATH));
        String version = "Basic";
        Map<String, String> playerMap = new HashMap<>();
        playerMap.put("Jackson", "Human");
        playerMap.put("Drew", "Human");
        playerMap.put("Ryan", "CPU");
        playerMap.put("Luke", "CPU");
        int pointsToWin = 70;
        boolean stackable = false;
        GameState expected = new GameState(version, playerMap, pointsToWin, stackable);
        assertTrue(expected.compareInitialParameters(controller.getModel()));
    }

    @Test
    void checkingModelObjectAfterLoadingNewInvalidFile(){
        assertFalse(controller.loadNewFile(INVALID_NEW_FILE_1_PATH));
        assertNull(controller.getModel());
    }

    @Test
    void manuallySettingGameParameters(){
        String version = "Basic";
        Map<String, String> playerMap = new HashMap<>();
        playerMap.put("player1", "Human");
        playerMap.put("Player 2", "Human");
        playerMap.put("Player3", "CPU");
        int pointsToWin = 778;
        boolean stackable = true;
        assertTrue(controller.setGameParameters(version, playerMap, pointsToWin, stackable));
    }

    @Test
    void manuallySettingInvalidGameParameters(){
        String version = "Basic";
        Map<String, String> playerMap = new HashMap<>();
        int pointsToWin = 9;
        boolean stackable = false;
        assertFalse(controller.setGameParameters(version, playerMap, pointsToWin, stackable));
    }

    @Test
    void manuallySettingInvalidGameParameters2(){
        String version = null;
        Map<String, String> playerMap = new HashMap<>();
        playerMap.put("Adam", "CPU");
        playerMap.put("Player 2", "Human");
        int pointsToWin = 99;
        boolean stackable = true;
        assertFalse(controller.setGameParameters(version, playerMap, pointsToWin, stackable));
    }

    @Test
    void manuallySettingInvalidGameParameters3(){
        String version = "Basic";
        Map<String, String> playerMap = new HashMap<>();
        playerMap.put("Drew", "Human");
        playerMap.put("Quentin", "Human");
        playerMap.put("Will Long", "CPU");
        int pointsToWin = -1;
        boolean stackable = false;
        assertFalse(controller.setGameParameters(version, playerMap, pointsToWin, stackable));
    }

    @Test
    void checkingModelObjectAfterManuallySettingGameParameters(){
        String version = "Basic";
        Map<String, String> playerMap = new HashMap<>();
        playerMap.put("player1", "Human");
        playerMap.put("Player 2", "Human");
        playerMap.put("Player3", "CPU");
        int pointsToWin = 778;
        boolean stackable = true;
        assertTrue(controller.setGameParameters(version, playerMap, pointsToWin, stackable));
        GameState expected = new GameState(version, playerMap, pointsToWin, stackable);
        assertTrue(expected.compareInitialParameters(controller.getModel()));
    }

    @Test
    void checkingModelObjectAfterManuallySettingInvalidGameParameters(){
        String version = null;
        Map<String, String> playerMap = new HashMap<>();
        int pointsToWin = -1;
        boolean stackable = false;
        assertFalse(controller.setGameParameters(version, playerMap, pointsToWin, stackable));
        assertNull(controller.getModel());
    }

    @Test
    void playingNewGameBeforeLoadingFileOrManuallySettingParameters(){
        assertFalse(controller.playNewGame());
    }

    @Test
    void playingNewGameAfterLoadingFile(){
        CardDisplay.initializeCards();
        assertTrue(controller.loadNewFile(VALID_NEW_FILE_1_PATH));
        runAsJFXAction(() -> controller.playNewGame());
        assertNotNull(controller.getUnoDisplay());
    }

    @Test
    void playingNewGameAfterLoadingInvalidFile(){
        CardDisplay.initializeCards();
        assertFalse(controller.loadNewFile(INVALID_NEW_FILE_1_PATH));
        runAsJFXAction(() -> controller.playNewGame());
        assertNull(controller.getUnoDisplay());
    }

    @Test
    void playingNewGameAfterManuallySettingParameters(){
        String version = "Basic";
        Map<String, String> playerMap = new HashMap<>();
        playerMap.put("player1", "Human");
        playerMap.put("Player 2", "Human");
        playerMap.put("Player3", "CPU");
        int pointsToWin = 778;
        boolean stackable = true;
        assertTrue(controller.setGameParameters(version, playerMap, pointsToWin, stackable));
        runAsJFXAction(() -> controller.playNewGame());
        assertNotNull(controller.getUnoDisplay());
    }

    @Test
    void playingNewGameAfterManuallySettingInvalidParameters(){
        String version = null;
        Map<String, String> playerMap = new HashMap<>();
        int pointsToWin = -1;
        boolean stackable = false;
        assertFalse(controller.setGameParameters(version, playerMap, pointsToWin, stackable));
        runAsJFXAction(() -> controller.playNewGame());
        assertNull(controller.getUnoDisplay());
    }

    // TODO: Save file testing!
    // TODO: Load existing file testing!
}
