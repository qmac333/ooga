package ooga.model.player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Supplier;
import ooga.model.cards.CardInterface;
import ooga.model.cards.ViewCardInterface;
import ooga.model.deck.CardPile;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.hand.Hand;

public class PlayerGroup implements PlayerGroupInterface {

  private final ResourceBundle playerResources = ResourceBundle.getBundle(
      "ooga.model.player.PlayerResources");

  private int myCurrentPlayer;
  private int myOrder;
  private boolean skipNext;
  private boolean skipEveryone;
  private GameStatePlayerInterface myGame;
  private Map<String, String> myPlayerMap;

  private List<PlayerInterface> myPlayers;

  public PlayerGroup(Map<String, String> playerMap, GameStatePlayerInterface game)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    myOrder = 1;
    myCurrentPlayer = 0;
    skipNext = false;
    skipEveryone = false;
    myGame = game;
    myPlayerMap = playerMap;
    myPlayers = new ArrayList<>();
    createPlayers();
  }

  @Override
  public List<ViewPlayerInterface> getViewPlayers() {
    List<ViewPlayerInterface> players = new ArrayList<>();
    for (PlayerInterface p : myPlayers){
      players.add((ViewPlayerInterface) p);
    }
    return players;
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
  public List<ViewCardInterface> getCurrentPlayerCards() {
    return myPlayers.get(myCurrentPlayer).getViewCards();
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
  public void discardCard(CardInterface card) {
    myGame.discardCard(card);
  }

  @Override
  public void setSuppliers(Supplier<Integer> integerSupplier, Supplier<String> stringSupplier) {
    for (PlayerInterface player : myPlayers){
      player.setSuppliers(integerSupplier, stringSupplier);
    }
  }

  @Override
  public void playTurn() {
    myPlayers.get(myCurrentPlayer).playCard();
    loadNextPlayer();
  }

  @Override
  public boolean canPlayCard(CardInterface card) {
    return myGame.canPlayCard(card);
  }

  @Override
  public void addPlayer(PlayerInterface player) {
    myPlayers.add(player);
  }

  @Override
  public int getCurrentPlayer() {
    return myCurrentPlayer;
  }

  @Override
  public Map<String, String> getPlayerMap() {
    return myPlayerMap;
  }

  @Override
  public List<Hand> getHands() {
    List<Hand> hands = new ArrayList<>();
    for (PlayerInterface player : myPlayers){
      hands.add(player.getMyHand());
    }
    return hands;
  }

  @Override
  public boolean userPicksCard() {
    String currentPlayerName = myPlayers.get(myCurrentPlayer).getName();
    return myPlayerMap.get(currentPlayerName).equals("Human");
  }

  @Override
  public void dealCards(CardPile deck, int cardsPerPlayer) {
    for (int i = 0; i < cardsPerPlayer; i++) {
      for (PlayerInterface player : myPlayers) {
        CardInterface newCard = deck.popTopCard();
        player.addCards(List.of(newCard));
      }
    }
  }

  @Override
  public void setOrder(int order) {
    myOrder = order;
  }

  @Override
  public void loadHands(List<Hand> handsToLoad) {
    for (int i = 0; i < handsToLoad.size(); i++){
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

  private void createPlayers()
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    for (String name : myPlayerMap.keySet()) {
      Class<?> playerClass = Class.forName(
          String.format(playerResources.getString("PlayerClassBase"),
              playerResources.getString(myPlayerMap.get(name))));
      Player player = (Player) playerClass.getDeclaredConstructor(String.class,
          PlayerGroupInterface.class).newInstance(name, this);
      myPlayers.add(player);
    }
  }
}
