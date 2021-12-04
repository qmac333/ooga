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
    private static final String VALID_NEW_FILE_1_PATH = Paths.get(".", "\\data\\configuration_files\\Test Files\\validNewFile1.json").toAbsolutePath().normalize().toString();
    private static final String VALID_NEW_FILE_2_PATH = Paths.get(".", "\\data\\configuration_files\\Test Files\\validNewFile2.json").toAbsolutePath().normalize().toString();
    private static final String INVALID_NEW_FILE_1_PATH = Paths.get(".", "\\data\\configuration_files\\Test Files\\invalidNewFile1.json").toAbsolutePath().normalize().toString();
    private static final String BASIC_4CPUs_PATH = Paths.get(".", "\\data\\configuration_files\\Example Files\\Basic_4CPUs.json").toAbsolutePath().normalize().toString();
    private static final String SAVE_FILENAME = "jUnitTest_SaveFile";
    private static final String INVALID_SAVE_FILENAME = "/this.won't/work/";
    private static final String SAVE_FILENAME_PATH = Paths.get(".", "\\data\\configuration_files\\Save Files\\" + SAVE_FILENAME + ".json").toAbsolutePath().normalize().toString();

    @Override
    public void start(Stage stage){
        controller = new UnoController(stage);
        CardDisplay.initializeCards();
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
        controller.setLanguage("Spanish");
    }

    @Test
    void pressingBackButton(){
        runAsJFXAction(() -> controller.backButtonHandler());
        assertNotNull(controller.getLanguageScreen());
    }

    @Test
    void loadingNewFile(){
        assertTrue(controller.loadFile(VALID_NEW_FILE_1_PATH));
        runAsJFXAction(() -> controller.playNewGame());
        assertNotNull(controller.getUnoDisplay());
    }

    @Test
    void loadingNewInvalidFile(){
        assertFalse(controller.loadFile(INVALID_NEW_FILE_1_PATH));
        runAsJFXAction(() -> controller.playNewGame());
        assertNull(controller.getUnoDisplay());
    }

    @Test
    void checkingModelObjectAfterLoadingNewFile(){
        assertTrue(controller.loadFile(VALID_NEW_FILE_1_PATH));
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
        assertTrue(controller.loadFile(VALID_NEW_FILE_1_PATH));
        assertTrue(controller.loadFile(VALID_NEW_FILE_2_PATH));
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
        assertFalse(controller.loadFile(INVALID_NEW_FILE_1_PATH));
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
        runAsJFXAction(() -> controller.playNewGame());
        assertNotNull(controller.getUnoDisplay());
    }

    @Test
    void manuallySettingInvalidGameParameters(){
        String version = "Basic";
        Map<String, String> playerMap = new HashMap<>();
        int pointsToWin = 9;
        boolean stackable = false;
        assertFalse(controller.setGameParameters(version, playerMap, pointsToWin, stackable));
        runAsJFXAction(() -> controller.playNewGame());
        assertNull(controller.getUnoDisplay());
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
        runAsJFXAction(() -> controller.playNewGame());
        assertNull(controller.getUnoDisplay());
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
        runAsJFXAction(() -> controller.playNewGame());
        assertNull(controller.getUnoDisplay());
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
    void playingNewGameBeforeLoadingFileOrManuallySettingParameters(){
        assertFalse(controller.playNewGame());
    }

    @Test
    void usingBackButtonAfterPlayingNewGame(){
        assertTrue(controller.loadFile(VALID_NEW_FILE_1_PATH));
        runAsJFXAction(() -> controller.playNewGame());
        assertNotNull(controller.getUnoDisplay());
        runAsJFXAction(() -> controller.backButtonHandler());
        assertNull(controller.getModel());
        assertNull(controller.getUnoDisplay());
    }


    @Test
    void savingAfterLoadingNewFile(){
        assertTrue(controller.loadFile(VALID_NEW_FILE_1_PATH));
        runAsJFXAction(() -> controller.playNewGame());
        assertNotNull(controller.getUnoDisplay());
        assertTrue(controller.saveCurrentFile(SAVE_FILENAME));
    }

    @Test
    void savingAfterManuallySettingParameters(){
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
        assertTrue(controller.saveCurrentFile(SAVE_FILENAME));
    }

    @Test
    void savingUsingInvalidFilename(){
        assertTrue(controller.loadFile(VALID_NEW_FILE_1_PATH));
        runAsJFXAction(() -> controller.playNewGame());
        assertNotNull(controller.getUnoDisplay());
        assertFalse(controller.saveCurrentFile(INVALID_SAVE_FILENAME));
    }

    @Test
    void loadingFileAfterSavingFile(){
        assertTrue(controller.loadFile(VALID_NEW_FILE_1_PATH));
        runAsJFXAction(() -> controller.playNewGame());
        assertNotNull(controller.getUnoDisplay());
        assertTrue(controller.saveCurrentFile(SAVE_FILENAME));
        runAsJFXAction(() -> controller.backButtonHandler());
        assertTrue(controller.loadFile(SAVE_FILENAME_PATH));
    }

    @Test
    void reloadingAndPlayingGameInProgress(){
        assertTrue(controller.loadFile(BASIC_4CPUs_PATH));
        runAsJFXAction(() -> controller.playNewGame());
        assertNotNull(controller.getUnoDisplay());
        assertTrue(controller.saveCurrentFile(SAVE_FILENAME));
        for(int i = 0; i < 10; i++){
            controller.getModel().playTurn();
        }
        assertTrue(controller.saveCurrentFile(SAVE_FILENAME));
        runAsJFXAction(() -> controller.backButtonHandler());
        assertTrue(controller.loadFile(SAVE_FILENAME_PATH));
        for(int i = 0; i < 10; i++){
            controller.getModel().playTurn();
        }
    }

    @Test
    void checkingModelAfterReloadingAndPlayingGameInProgress(){
        assertTrue(controller.loadFile(BASIC_4CPUs_PATH));
        runAsJFXAction(() -> controller.playNewGame());
        assertNotNull(controller.getUnoDisplay());
        assertTrue(controller.saveCurrentFile(SAVE_FILENAME));
        for(int i = 0; i < 10; i++){
            controller.getModel().playTurn();
        }
        assertTrue(controller.saveCurrentFile(SAVE_FILENAME));
        GameState expected = controller.getModel();
        runAsJFXAction(() -> controller.backButtonHandler());
        assertTrue(controller.loadFile(SAVE_FILENAME_PATH));
        assertTrue(controller.getModel().compareGameInProgressParameters(expected));
        for(int i = 0; i < 10; i++){
            controller.getModel().playTurn();
        }
        assertFalse(controller.getModel().compareGameInProgressParameters(expected));
    }
}