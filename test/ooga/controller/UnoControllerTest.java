package ooga.controller;

import static org.junit.jupiter.api.Assertions.*;

import javafx.stage.Stage;
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
    void loadingNewFile(){
        assertTrue(controller.loadNewFile(VALID_NEW_FILE_1_PATH));
    }

    @Test
    void loadingNewInvalidFile(){
        assertFalse(controller.loadNewFile(INVALID_NEW_FILE_1_PATH));
    }

    @Test
    void checkingModelObjectAfterLoadingNewFile(){
        Map<String, String> playerMap = new HashMap<>();
        playerMap.put("Andrew", "Human");
        playerMap.put("Drew", "CPU");
        playerMap.put("Quentin", "Human");
        GameState expected = new GameState("Basic", playerMap, 500, true);
        assertTrue(controller.loadNewFile(VALID_NEW_FILE_1_PATH));
        assertTrue(expected.compareInitialParameters(controller.getModel()));
    }

    @Test
    void checkingModelObjectAfterLoadingTwoNewFilesInARow(){
        controller.loadNewFile(VALID_NEW_FILE_1_PATH);
        Map<String, String> playerMap = new HashMap<>();
        playerMap.put("Jackson", "Human");
        playerMap.put("Drew", "Human");
        playerMap.put("Ryan", "CPU");
        playerMap.put("Luke", "CPU");
        GameState expected = new GameState("Basic", playerMap, 70, false);
        assertTrue(controller.loadNewFile(VALID_NEW_FILE_2_PATH));
        assertTrue(expected.compareInitialParameters(controller.getModel()));
    }

    // TODO: test invalid configuration files (wrong format, additional parameters, missing parameters) - Moshi should handle this but not sure how to verify?
}
