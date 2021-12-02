package ooga.controller;
import ooga.model.cards.CardInterface;
import ooga.model.hand.Hand;

import java.util.List;
import java.util.Map;

public class GameStateJson {

    /**
     * Initial game parameters:
     */
    private String version;
    private Map<String, String> playerMap;
    private int points;
    private boolean stackable;

    /**
     * Game in progress parameters:
     */
    private int currentPlayer = -1;
    private List<Hand> myHands = null;
    private List<CardInterface> myDiscardList = null;
    private List<CardInterface> myDeckList = null;
    private int impendingDraw = -1;
    private boolean skipNext = false;
    private boolean skipEveryone = false;
    private int order = -1;
    private int[] playerPoints = null;
    private boolean uno = false;

    @SuppressWarnings("unused")
    private GameStateJson(){
    }

    public GameStateJson(String version, Map<String, String> playerMap, int points, boolean stackable){
        this.version = version;
        this.playerMap = playerMap;
        this.points = points;
        this.stackable = stackable;
    }

    public GameStateJson(String version, Map<String, String> playerMap, int points, boolean stackable, int currentPlayer,
                         List<Hand> myHands, List<CardInterface> myDiscardPile, List<CardInterface> myDeck, int impendingDraw, boolean skipNext,
                         boolean skipEveryone, int order, int[] playerPoints, boolean uno){
        this.version = version;
        this.playerMap = playerMap;
        this.points = points;
        this.stackable = stackable;

        this.currentPlayer = currentPlayer;
        this.myHands = myHands;
        this.myDiscardList = myDiscardPile;
        this.myDeckList = myDeck;
        this.impendingDraw = impendingDraw;
        this.skipNext = skipNext;
        this.skipEveryone = skipEveryone;
        this.order = order;
        this.playerPoints = playerPoints;
        this.uno = uno;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getPlayerMap() {
        return playerMap;
    }

    public int getPoints() {
        return points;
    }

    public boolean getStackable(){
        return stackable;
    }

    public List<Hand> getMyHands() {
        return myHands;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public List<CardInterface> getMyDiscardList() {
        return myDiscardList;
    }

    public List<CardInterface> getMyDeckList() {
        return myDeckList;
    }

    public int getImpendingDraw() {
        return impendingDraw;
    }

    public boolean getSkipNext() {
        return skipNext;
    }

    public boolean getSkipEveryone() {
        return skipEveryone;
    }

    public int getOrder() {
        return order;
    }

    public int[] getPlayerPoints() {
        return playerPoints;
    }

    public boolean getUno() {
        return uno;
    }
}
