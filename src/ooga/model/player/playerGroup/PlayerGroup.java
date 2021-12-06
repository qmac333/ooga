package ooga.model.player.playerGroup;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.model.cards.CardInterface;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.hand.Hand;
import ooga.model.instanceCreation.ReflectionErrorException;
import ooga.model.instanceCreation.ReflectionHandler;
import ooga.model.instanceCreation.ReflectionHandlerInterface;
import ooga.model.player.player.PlayerGameInterface;
import org.jetbrains.annotations.NotNull;

public class PlayerGroup implements PlayerGroupPlayerInterface, PlayerGroupGameInterface {

  private static final String BUNDLE_PATH = "ooga.model.player.playerGroup.PlayerGroupResources";
  private static final String PLAYER_BASE = "PlayerClassBase";
  private static final String STARTING_ORDER = "StartingOrder";
  private static final String FLIP_ORDER = "FlipOrder";
  private static final String UNO = "UnoHandSize";
  private static final String SKIP = "SkipNextAdder";

  private static final ResourceBundle playerResources = ResourceBundle.getBundle(BUNDLE_PATH);

  private int myCurrentPlayer;
  private int myOrder;
  private boolean skipNext;
  private boolean skipEveryone;
  private final GameStatePlayerInterface myGame;
  private final Map<String, String> myPlayerMap;
  private boolean unoCalled;

  private final List<PlayerGameInterface> myPlayers;

  public PlayerGroup(Map<String, String> playerMap, GameStatePlayerInterface game)
      throws ReflectionErrorException {
    myOrder = Integer.parseInt(playerResources.getString(STARTING_ORDER));
    myCurrentPlayer = 0;
    skipNext = false;
    skipEveryone = false;
    myGame = game;
    myPlayerMap = playerMap;
    myPlayers = new ArrayList<>();
    unoCalled = false;
    createPlayers();
  }

  @Override
  public PlayerGameInterface getCurrentPlayer() {
    return myPlayers.get(myCurrentPlayer);
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
    myOrder *= Integer.parseInt(playerResources.getString(FLIP_ORDER));
  }

  @Override
  public void flipGame() {
    for (PlayerGameInterface player : myPlayers) {
      player.flipHand();
    }
  }

  @Override
  public void enforceDraw(int drawAmount) {
    myGame.addDraw(drawAmount);
  }

  @Override
  public void discardCard(CardInterface card) {
    myGame.discardCard(card);
  }

  @Override
  public void playTurn() {
    myPlayers.get(myCurrentPlayer).playCard();
    checkUno();
  }

  @Override
  public boolean canPlayCard(CardInterface card) {
    return myGame.canPlayCard(card);
  }

  @Override
  public void addPlayer(PlayerGameInterface player) {
    myPlayers.add(player);
  }

  @Override
  public int getCurrentPlayerIndex() {
    return myCurrentPlayer;
  }

  @Override
  public Map<String, String> getPlayerMap() {
    return myPlayerMap;
  }

  @Override
  public boolean userPicksCard() {
    String currentPlayerName = myPlayers.get(myCurrentPlayer).getName();
    return myPlayerMap.get(currentPlayerName).equals("Human");
  }

  @Override
  public void setOrder(int order) {
    myOrder = order;
  }

  @Override
  public void setCurrent(int player) {
    myCurrentPlayer = player;
  }

  @Override
  public void loadHands(List<Hand> handsToLoad) {
    for (int i = 0; i < handsToLoad.size(); i++) {
      myPlayers.get(i).loadHand(handsToLoad.get(i));
    }
  }

  @Override
  public int getMyOrder() {
    return myOrder;
  }

  @Override
  public Collection<CardInterface> noPlayDraw() {
    return myGame.noPlayDraw();
  }

  @Override
  public void setUnoCalled(boolean called) {
    unoCalled = called;
  }

  @Override
  public void loadNextPlayer() {
    int boostedCurrentPlayer = myCurrentPlayer + myPlayers.size();
    if (skipNext) {
      myCurrentPlayer =
          (boostedCurrentPlayer + Integer.parseInt(playerResources.getString(SKIP)) * myOrder)
              % myPlayers.size();
      skipNext = false;
    } else if (skipEveryone) {
      skipEveryone = false;
    } else {
      myCurrentPlayer = (boostedCurrentPlayer + myOrder) % myPlayers.size();
    }
  }

  @Override
  public void countAndAwardPoints() {
    int totalPoints = 0;
    for (PlayerGameInterface p : myPlayers) {
      totalPoints += p.getNumPoints();
    }
    myPlayers.get(myCurrentPlayer).awardPoints(totalPoints);
  }

  private void createPlayers() throws ReflectionErrorException {
    ReflectionHandlerInterface reflectionHandlerInterface = new ReflectionHandler();
    for (String name : myPlayerMap.keySet()) {
      myPlayers.add(reflectionHandlerInterface.getPlayer(name, this,
          playerResources.getString(myPlayerMap.get(name))));
    }
  }

  private void checkUno() {
    if (myPlayers.get(myCurrentPlayer).getHandSize() == Integer.parseInt(
        playerResources.getString(UNO))) {
      if (!unoCalled) {
        myPlayers.get(myCurrentPlayer).addCards(myGame.getUnoPunishment());
      }
    }
    unoCalled = false;
  }

  @NotNull
  @Override
  public Iterator<PlayerGameInterface> iterator() {
    return new PlayerGroupIterator();
  }

  private class PlayerGroupIterator implements Iterator<PlayerGameInterface> {

    private int position = 0;

    @Override
    public boolean hasNext() {
      return position < myPlayers.size();
    }

    @Override
    public PlayerGameInterface next() {
      if (hasNext()) {
        return myPlayers.get(position++);
      }
      return null;
    }
  }
}
