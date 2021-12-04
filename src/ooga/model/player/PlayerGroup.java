package ooga.model.player;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import ooga.model.gameState.GameStatePlayerInterface;

public class PlayerGroup implements PlayerGroupInterface {

  private int myCurrentPlayer;
  private int myOrder;
  private boolean skipNext;
  private boolean skipEveryone;
  private GameStatePlayerInterface myGame;

  private List<PlayerInterface> myPlayers;

  public PlayerGroup(Map<String, String> playerMap, GameStatePlayerInterface game){
    myOrder = 1;
    myCurrentPlayer = 0;
    skipNext = false;
    skipEveryone = false;
    myGame = game;
  }

  @Override
  public Collection<Integer> getCurrentPlayerValidIndexes() {
    return myPlayers.get(myCurrentPlayer).getValidIndexes();
  }

  @Override
  public void skipEveryone() {
    skipEveryone = true;
  }

  @Override
  public void skipNextPlayer() {
    skipNext = true;
  }

  @Override
  public void reverseOrder() {
    myOrder *= -1;
  }

  @Override
  public void flipGame() {
    for (PlayerInterface player : myPlayers){
      player.flipHand();
    }
  }

  @Override
  public void enforceDraw(int drawAmount) {
    myGame.addDraw(drawAmount);
  }

  @Override
  public List<Integer> getPoints() {
    return null;
  }

  @Override
  public List<Integer> getHandSizes() {
    return null;
  }

  @Override
  public List<String> getNames() {
    return null;
  }

  @Override
  public void setSuppliers(Supplier<Integer> integerSupplier, Supplier<String> stringSupplier) {

  }

  @Override
  public void playTurn() {
    myPlayers.get(myCurrentPlayer).playCard();
    loadNextPlayer();
  }

  private void loadNextPlayer(){
    int boostedCurrentPlayer = myCurrentPlayer + myPlayers.size();
    if (skipNext) {
      myCurrentPlayer = (boostedCurrentPlayer + 2 * myOrder) % myPlayers.size();
      skipNext = false;
    } else if (skipEveryone) {
      skipEveryone = false;
    } else {
      myCurrentPlayer = (boostedCurrentPlayer + myOrder) % myPlayers.size();
    }
  }
}
