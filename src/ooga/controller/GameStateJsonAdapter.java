package ooga.controller;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;
import ooga.model.gameState.GameState;

import java.util.HashMap;
import java.util.Map;

public class GameStateJsonAdapter {
    @FromJson GameState gameStateFromJson(GameStateJson myGameStateJson){
        String version = myGameStateJson.getVersion();
        Map<String, String> players = myGameStateJson.getPlayers();
        int points = myGameStateJson.getPoints();
        boolean stackable = myGameStateJson.getStackable();

        //TODO: pass above parameters to GameState's constructor once Will/Paul implement them
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
