package ooga.model;

import java.util.List;
import java.util.Map;

public class GameState implements GameStateInterface{
    @Override
    public void playTurn() {

    }

    @Override
    public int getCurrentPlayer() {
        return 0;
    }

    @Override
    public Map<Integer, List<List<String>>> getCurrentPlayerCards() {
        return null;
    }
}
