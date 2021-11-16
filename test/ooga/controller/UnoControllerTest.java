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
    private static final String ABSOLUTE_FILEPATH_1 = Paths.get(".").toAbsolutePath().normalize() + "/data/configurationfiles/example1.json";
    private static final String ABSOLUTE_FILEPATH_2 = Paths.get(".").toAbsolutePath().normalize() + "/data/configurationfiles/example2.json";

    @Override
    public void start(Stage stage){
        controller = new UnoController(stage);
    }

    @Test
    void creatingModelFromJsonFile(){
        Map<String, String> playerMap = new HashMap<>();
        playerMap.put("Andrew", "Human");
        playerMap.put("Drew", "CPU");
        playerMap.put("Quentin", "Human");
        GameState expected = new GameState("Basic", playerMap, 500, true);
        controller.loadNewHandler(ABSOLUTE_FILEPATH_1);
        assertTrue(expected.compareInitialParameters(controller.getModel()));
    }

    @Test
    void loadingTwoFilesInARow(){
        controller.loadNewHandler(ABSOLUTE_FILEPATH_1);
        Map<String, String> playerMap = new HashMap<>();
        playerMap.put("Jackson", "Human");
        playerMap.put("Drew", "Human");
        playerMap.put("Ryan", "CPU");
        playerMap.put("Luke", "CPU");
        GameState expected = new GameState("Special", playerMap, 70, false);
        controller.loadNewHandler(ABSOLUTE_FILEPATH_2);
        assertTrue(expected.compareInitialParameters(controller.getModel()));
    }

    // TODO: test invalid configuration files (wrong format, additional parameters, missing parameters) - Moshi should handle this but not sure how to verify?
}
