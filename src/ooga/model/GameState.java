package ooga.model;

import java.util.List;
import java.util.Map;

public class GameState implements GameStateInterface{

    private int order;
    private int currentPlayer;
    private List<Player> players;
    private Card lastCardThrown;

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
        int boostedCurrentPlayer = currentPlayer + players.size();
        if(!skipNext){
            currentPlayer = (boostedCurrentPlayer + order) % players.size();
        }
        else{
            currentPlayer = (boostedCurrentPlayer + 2 * order) % players.size();
            skipNext = false;
        }
    }

}
