package ooga.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameState implements GameStateInterface, GameStateViewInterface {

  private int order;
  private int currentPlayer;
  private List<Player> players;
  private Card lastCardThrown;

  private boolean setNextPlayerDrawTwo;

  private boolean skipNext;

  public GameState() {
    order = 1;
    skipNext = false;
    setNextPlayerDrawTwo = false;
    players = new ArrayList<>();
  }

  @Override
  public List<String> getPlayerNames() {
    return null;
  }

  @Override
  public List<Integer> getCardCounts() {
    return null;
  }

  @Override
  public void setLastCardThrown(Card c) {
    lastCardThrown = c;
  }


  @Override
  public String getLastCardThrownType() {
    return lastCardThrown.getType();
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
  public void playTurn() {
    // Basically tell the current player to play their turn
    loadNextPlayer();
  }

  @Override
  public int getGameplayDirection() {
    return order;
  }

  @Override
  public void addPlayer(Player p) {
    players.add(p);
  }

  @Override
  public int getCurrentPlayer() {
    return currentPlayer;
  }

  @Override
  public List<List<String>> getCurrentPlayerCards() {
    return null;
  }

  @Override
  public void setNextPlayerDrawTwo(boolean truthVal) {
    setNextPlayerDrawTwo = truthVal;
  }

  private void loadNextPlayer() {
    int boostedCurrentPlayer = currentPlayer + players.size();
    if (!skipNext) {
      currentPlayer = (boostedCurrentPlayer + order) % players.size();
    } else {
      currentPlayer = (boostedCurrentPlayer + 2 * order) % players.size();
      skipNext = false;
    }
  }
}