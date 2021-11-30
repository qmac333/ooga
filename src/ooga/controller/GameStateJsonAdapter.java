package ooga.controller;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;
import ooga.model.gameState.GameState;

import java.util.HashMap;
import java.util.Map;

// Note to self: if an optional parameter is included in the config file, figure out how Moshi converts to GameStateJSON
// when the optional parameter is missing, and then once that is resolved, call the corresponding method in GameState
// to load in the option parameter if it is present
/**
 * Class used to tell Moshi how to convert between GameStateJson (dummy class that has instance variables of the exact
 * same format as the JSON file) and GameState (the actual model class)
 */
public class GameStateJsonAdapter {
    @FromJson GameState gameStateFromJson(GameStateJson myGameStateJson){
        String version = myGameStateJson.getVersion();
        Map<String, String> playerMap = myGameStateJson.getPlayers();
        int points = myGameStateJson.getPoints();
        boolean stackable = myGameStateJson.getStackable();

        GameState myGameState = new GameState(version, playerMap, points, stackable);
        return myGameState;
    }

    @ToJson GameStateJson gameStateToJson(GameState myGameState){
        String version = myGameState.getVersion();
        Map<String, String> playerMap = myGameState.getPlayerMap();
        int points = myGameState.getPointsToWin();
        boolean stackable = myGameState.getStackable();

        GameStateJson myGameStateJson = new GameStateJson(version, playerMap, points, stackable);
        return myGameStateJson;
    }
}
