package ooga.controller;

import static org.junit.jupiter.api.Assertions.*;

import javafx.stage.Stage;
import ooga.model.gameState.GameState;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.HashMap;
import java.util.Map;

public class UnoControllerTest extends DukeApplicationTest {
    private UnoController controller;
    private static final String ABSOLUTE_FILEPATH = "C:/Users/drewp/IdeaProjects/ooga_team05/doc/plan/data/config.json";

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
        controller.loadNewHandler(ABSOLUTE_FILEPATH);
        assertTrue(expected.equals(controller.getModel()));
    }
}
