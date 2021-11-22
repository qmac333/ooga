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
 * Class used to tell Moshi how to convert from GameStateJson (dummy class that has instance variables of the exact same format
 * as the JSON file) and GameState (the actual model class)
 */
public class GameStateJsonAdapter {
    @FromJson GameState gameStateFromJson(GameStateJson myGameStateJson){
        String version = myGameStateJson.getVersion();
        Map<String, String> players = myGameStateJson.getPlayers();
        int points = myGameStateJson.getPoints();
        boolean stackable = myGameStateJson.getStackable();

        GameState myGameState = new GameState(version, players, points, stackable);
        return myGameState;
    }

    @ToJson GameStateJson gameStateToJson(GameState myGameState){
        // TODO: get these parameters from myGameState!
        String version = "";
        Map<String, String> players = new HashMap<>();
        int points = 0;
        boolean stackable = false;

        GameStateJson myGameStateJson = new GameStateJson(version, players, points, stackable);
        return myGameStateJson;
    }
}
