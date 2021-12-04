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
    private static final String SAVE_FILENAME_1 = "jUnitTest_savingAfterLoadingNewFile";
    private static final String SAVE_FILENAME_2 = "jUnitTest_savingAfterManuallySettingParameters";
    private static final String SAVE_FILENAME_3 ="jUnitTest_loadingFileAfterSavingFile";
    private static final String SAVE_FILENAME_4 ="jUnitTest_reloadingAndPlayingGameInProgress";
    private static final String INVALID_SAVE_FILENAME_1 = "/this.won't/work/";
    private static final String SAVE_FILENAME_3_PATH = Paths.get(".", "\\data\\configuration_files\\Save Files\\" + SAVE_FILENAME_3 + ".json").toAbsolutePath().normalize().toString();
    private static final String SAVE_FILENAME_4_PATH = Paths.get(".", "\\data\\configuration_files\\Save Files\\" + SAVE_FILENAME_4 + ".json").toAbsolutePath().normalize().toString();

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
    }

    @Test
    void loadingNewInvalidFile(){
        assertFalse(controller.loadFile(INVALID_NEW_FILE_1_PATH));
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
    void playingNewGameAfterLoadingNewFile(){
        assertTrue(controller.loadFile(VALID_NEW_FILE_1_PATH));
        runAsJFXAction(() -> controller.playNewGame());
        assertNotNull(controller.getUnoDisplay());
    }

    @Test
    void playingNewGameAfterLoadingNewInvalidFile(){
        assertFalse(controller.loadFile(INVALID_NEW_FILE_1_PATH));
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
        assertTrue(controller.saveCurrentFile(SAVE_FILENAME_1));
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
        assertTrue(controller.saveCurrentFile(SAVE_FILENAME_2));
    }

    @Test
    void savingUsingInvalidFilename(){
        assertTrue(controller.loadFile(VALID_NEW_FILE_1_PATH));
        runAsJFXAction(() -> controller.playNewGame());
        assertNotNull(controller.getUnoDisplay());
        assertFalse(controller.saveCurrentFile(INVALID_SAVE_FILENAME_1));
    }

    @Test
    void loadingFileAfterSavingFile(){
        assertTrue(controller.loadFile(VALID_NEW_FILE_1_PATH));
        runAsJFXAction(() -> controller.playNewGame());
        assertNotNull(controller.getUnoDisplay());
        assertTrue(controller.saveCurrentFile(SAVE_FILENAME_3));
        runAsJFXAction(() -> controller.backButtonHandler());
        assertTrue(controller.loadFile(SAVE_FILENAME_3_PATH));
    }

    @Test
    void reloadingAndPlayingGameInProgress(){
        assertTrue(controller.loadFile(BASIC_4CPUs_PATH));
        runAsJFXAction(() -> controller.playNewGame());
        assertNotNull(controller.getUnoDisplay());
        assertTrue(controller.saveCurrentFile(SAVE_FILENAME_4));
        for(int i = 0; i < 10; i++){
            controller.getModel().playTurn();
        }
        assertTrue(controller.saveCurrentFile(SAVE_FILENAME_4));
        runAsJFXAction(() -> controller.backButtonHandler());
        assertTrue(controller.loadFile(SAVE_FILENAME_4_PATH));
        for(int i = 0; i < 10; i++){
            controller.getModel().playTurn();
        }
    }

    @Test
    void checkingModelAfterReloadingAndPlayingGameInProgress(){
        assertTrue(controller.loadFile(BASIC_4CPUs_PATH));
        runAsJFXAction(() -> controller.playNewGame());
        assertNotNull(controller.getUnoDisplay());
        assertTrue(controller.saveCurrentFile(SAVE_FILENAME_4));
        for(int i = 0; i < 10; i++){
            controller.getModel().playTurn();
        }
        assertTrue(controller.saveCurrentFile(SAVE_FILENAME_4));
        GameState expected = controller.getModel();
        runAsJFXAction(() -> controller.backButtonHandler());
        assertTrue(controller.loadFile(SAVE_FILENAME_4_PATH));
        assertTrue(controller.getModel().compareGameInProgressParameters(expected));
    }


    // TODO: Figure out why card color says black initially for reloaded game where the first card of discard pile is wild card
    // TODO: Check model after loading game in progress (similar to earlier tests)
}