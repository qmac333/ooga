package ooga.controller;
import java.util.Map;

public class GameStateJson {
    private String Version;
    private Map<String, String> Players;
    private int Points;
    private boolean Stackable;

    public GameStateJson(String version, Map<String, String> players, int points, boolean stackable){
        Version = version;
        Players = players;
        Points = points;
        Stackable = stackable;
    }

    public String getVersion() {
        return Version;
    }

    public Map<String, String> getPlayers() {
        return Players;
    }

    public int getPoints() {
        return Points;
    }

    public boolean getStackable(){
        return Stackable;
    }
}
