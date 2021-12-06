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
    private static final String INVALID_GAME_IN_PROGRESS_FILE_1_PATH = Paths.get(".", "\\data\\configuration_files\\Test Files\\invalidGameInProgressFile1.json").toAbsolutePath().normalize().toString();
    private static final String BASIC_4CPUs_PATH = Paths.get(".", "\\data\\configuration_files\\Example Files\\Basic_4CPUs.json").toAbsolutePath().normalize().toString();
    private static final String SAVE_FILENAME = "jUnitTest_SaveFile";
    private static final String INVALID_SAVE_FILENAME = "/this.won't/work/";
    private static final String SAVE_FILENAME_PATH = Paths.get(".", "\\data\\configuration_files\\Save Files\\" + SAVE_FILENAME + ".json").toAbsolutePath().normalize().toString();

    private static final String DEFAULT_LANGUAGE = "English";
    private static final String DEFAULT_MOD = "Traditional";

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
    void creatingSplashScreenAndChangingLanguage(){
        runAsJFXAction(() -> controller.createSplashScreen(DEFAULT_LANGUAGE));
        assertNotNull(controller.getSplashScreen());
        controller.setLanguage("Spanish");
    }

    @Test
    void loadingNewFile(){
        assertTrue(controller.loadFile(VALID_NEW_FILE_1_PATH));
        runAsJFXAction(() -> controller.playNewGame(DEFAULT_MOD));
        assertNotNull(controller.getUnoDisplay());
    }

    @Test
    void loadingInvalidNewFile(){
        assertFalse(controller.loadFile(INVALID_NEW_FILE_1_PATH));
        runAsJFXAction(() -> controller.playNewGame(DEFAULT_MOD));
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
    void manuallySettingGameParameters(){
        String version = "Basic";
        Map<String, String> playerMap = new HashMap<>();
        playerMap.put("player1", "Human");
        playerMap.put("Player 2", "Human");
        playerMap.put("Player3", "CPU");
        int pointsToWin = 778;
        boolean stackable = true;
        assertTrue(controller.setGameParameters(version, playerMap, pointsToWin, stackable));
        runAsJFXAction(() -> controller.playNewGame(DEFAULT_MOD));
        assertNotNull(controller.getUnoDisplay());
    }

    @Test
    void manuallySettingInvalidGameParameters(){
        String version1 = "Basic";
        Map<String, String> playerMap1 = new HashMap<>();
        int pointsToWin1 = 9;
        boolean stackable1 = false;
        assertFalse(controller.setGameParameters(version1, playerMap1, pointsToWin1, stackable1));
        runAsJFXAction(() -> controller.playNewGame(DEFAULT_MOD));
        assertNull(controller.getUnoDisplay());

        String version2 = null;
        Map<String, String> playerMap2 = new HashMap<>();
        playerMap2.put("Adam", "CPU");
        playerMap2.put("Player 2", "Human");
        int pointsToWin2 = 99;
        boolean stackable2 = true;
        assertFalse(controller.setGameParameters(version2, playerMap2, pointsToWin2, stackable2));
        runAsJFXAction(() -> controller.playNewGame(DEFAULT_MOD));
        assertNull(controller.getUnoDisplay());

        String version3 = "Basic";
        Map<String, String> playerMap3 = new HashMap<>();
        playerMap3.put("Drew", "Human");
        playerMap3.put("Quentin", "Human");
        playerMap3.put("Will Long", "CPU");
        int pointsToWin3 = -1;
        boolean stackable3 = false;
        assertFalse(controller.setGameParameters(version3, playerMap3, pointsToWin3, stackable3));
        runAsJFXAction(() -> controller.playNewGame(DEFAULT_MOD));
        assertNull(controller.getUnoDisplay());
    }

    @Test
    void checkingModelAfterManuallySettingGameParameters(){
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
        assertFalse(controller.playNewGame(DEFAULT_MOD));
    }

    @Test
    void checkingModAfterPlayingNewGame(){
        String expected = DEFAULT_MOD;
        assertTrue(controller.loadFile(VALID_NEW_FILE_1_PATH));
        runAsJFXAction(() -> controller.playNewGame(DEFAULT_MOD));
        assertNotNull(controller.getUnoDisplay());
        assertTrue(controller.getMod().equals(expected));
    }

    @Test
    void usingBackButtonAfterPlayingNewGame(){
        assertTrue(controller.loadFile(VALID_NEW_FILE_1_PATH));
        runAsJFXAction(() -> controller.playNewGame(DEFAULT_MOD));
        assertNotNull(controller.getUnoDisplay());
        runAsJFXAction(() -> controller.toSplashScreen());
        assertNull(controller.getModel());
        assertNull(controller.getUnoDisplay());
        assertNotNull(controller.getLanguageScreen());
    }


    @Test
    void savingAfterLoadingNewFile(){
        assertTrue(controller.loadFile(VALID_NEW_FILE_1_PATH));
        runAsJFXAction(() -> controller.playNewGame(DEFAULT_MOD));
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
        runAsJFXAction(() -> controller.playNewGame(DEFAULT_MOD));
        assertNotNull(controller.getUnoDisplay());
        assertTrue(controller.saveCurrentFile(SAVE_FILENAME));
    }

    @Test
    void savingUsingInvalidFilename(){
        assertTrue(controller.loadFile(VALID_NEW_FILE_1_PATH));
        runAsJFXAction(() -> controller.playNewGame(DEFAULT_MOD));
        assertNotNull(controller.getUnoDisplay());
        assertFalse(controller.saveCurrentFile(INVALID_SAVE_FILENAME));
    }

    @Test
    void loadingFileAfterSavingFile(){
        assertTrue(controller.loadFile(VALID_NEW_FILE_1_PATH));
        runAsJFXAction(() -> controller.playNewGame(DEFAULT_MOD));
        assertNotNull(controller.getUnoDisplay());
        assertTrue(controller.saveCurrentFile(SAVE_FILENAME));
        runAsJFXAction(() -> controller.toSplashScreen());
        assertTrue(controller.loadFile(SAVE_FILENAME_PATH));
    }

    @Test
    void loadingInvalidGameInProgressFile(){
        assertFalse(controller.loadFile(INVALID_GAME_IN_PROGRESS_FILE_1_PATH));
        runAsJFXAction(() -> controller.playNewGame(DEFAULT_MOD));
        assertNull(controller.getUnoDisplay());
    }

    @Test
    void reloadingAndPlayingGameInProgress(){
        assertTrue(controller.loadFile(BASIC_4CPUs_PATH));
        runAsJFXAction(() -> controller.playNewGame(DEFAULT_MOD));
        assertNotNull(controller.getUnoDisplay());
        assertTrue(controller.saveCurrentFile(SAVE_FILENAME));
        simulateGame(10);
        assertTrue(controller.saveCurrentFile(SAVE_FILENAME));
        runAsJFXAction(() -> controller.toSplashScreen());
        assertTrue(controller.loadFile(SAVE_FILENAME_PATH));
        simulateGame(10);
    }

    @Test
    void checkingModelAfterReloadingAndPlayingGameInProgress(){
        assertTrue(controller.loadFile(BASIC_4CPUs_PATH));
        runAsJFXAction(() -> controller.playNewGame(DEFAULT_MOD));
        assertNotNull(controller.getUnoDisplay());
        assertTrue(controller.saveCurrentFile(SAVE_FILENAME));
        simulateGame(10);
        assertTrue(controller.saveCurrentFile(SAVE_FILENAME));
        GameState expected = controller.getModel();
        runAsJFXAction(() -> controller.toSplashScreen());
        assertTrue(controller.loadFile(SAVE_FILENAME_PATH));
        assertTrue(controller.getModel().compareGameInProgressParameters(expected));
        simulateGame(10);
        assertFalse(controller.getModel().compareGameInProgressParameters(expected));
    }

    private void simulateGame(int numTurns){
        for(int i = 0; i < numTurns; i++){
            controller.getModel().playTurn();
        }
    }
}