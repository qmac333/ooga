package ooga.controller;

import java.util.Map;

public class GameStateJSON {
    private String Version;
    private String Mod;
    private String Cards;
    private Map<String, String> Players;
    private int Points;
    private int HeadCount;
    private boolean Stackable;

    public GameStateJSON(String version, String mod, String cards, Map<String, String> players, int points, int headcount, boolean stackable){
        Version = version;
        Mod = mod;
        Cards = cards;
        Players = players;
        Points = points;
        HeadCount = headcount;
        Stackable = stackable;
    }

    public String getVersion() {
        return Version;
    }

    public String getMod() {
        return Mod;
    }

    public String getCards() {
        return Cards;
    }

    public Map<String, String> getPlayers() {
        return Players;
    }

    public int getPoints() {
        return Points;
    }

    public int getHeadCount() {
        return HeadCount;
    }

    public boolean getStackable(){
        return Stackable;
    }
}
