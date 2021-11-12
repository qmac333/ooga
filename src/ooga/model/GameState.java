package ooga.model;

import java.util.List;
import java.util.Map;

public class GameState implements GameStateInterface{

    private int order;
    private int currentPlayer;
    private List<Player> players;

    private boolean skipNext;

    public GameState(){
        order = 1;
        skipNext = false;
    }

    @Override
    public void playTurn() {

    }

    @Override
    public void reverseGamePlay() {
        order *= -1;
    }

    @Override
    public void skipNextPlayer() {
        skipNext = true;
    }

    @Override
    public void addPlayer(Player p) {

    }

    @Override
    public int getCurrentPlayer() {
        return 0;
    }

    @Override
    public Map<Integer, List<List<String>>> getCurrentPlayerCards() {
        return null;
    }

    private void loadNextPlayer(){

        if(!skipNext){
            if((currentPlayer == 0 && order == -1){
            }

        }
    }

    private void incrementPlayer(){
        currentPlayer = currentPlayer += order;
    }
}
