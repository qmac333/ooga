package ooga.model.gameState;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import java.util.function.Supplier;
import ooga.model.cards.CardInterface;
import ooga.model.cards.ViewCardInterface;
import ooga.model.deck.CardPile;
import ooga.model.deck.CardPileViewInterface;
import ooga.model.deck.UnoDeck;
import ooga.model.drawRule.DrawRuleInterface;
import ooga.model.hand.Hand;
import ooga.model.player.Player;

import ooga.model.player.PlayerInterface;
import ooga.model.rules.RuleInterface;

public class GameState implements GameStateInterface, GameStateViewInterface,
    GameStatePlayerInterface, GameStateDrawInterface {

  private final ResourceBundle gameStateResources = ResourceBundle.getBundle(
      "ooga.model.gameState.GameStateResources");

  private int currentPlayer;
  private List<Player> myPlayers;
  private CardPile myDiscardPile;
  private CardPile myDeck;

  private int impendingDraw;
  private boolean skipNext;
  private boolean skipEveryone;
  private int order;

  private String version;
  private List<RuleInterface> myRules;
  private DrawRuleInterface myDrawRule;
  private Map<String, String> playerMap;
  private int[] playerPoints;
  private boolean stackable;
  private final int pointsToWin;


  private boolean uno;
  private boolean endGame;
  private final static int NUM_CARDS_PER_PLAYER = 7;


  public GameState(String version, Map<String, String> playerMap, int pointsToWin,
      boolean stackable) {
    order = 1;
    skipNext = false;
    skipEveryone = false;
    impendingDraw = 0;
    this.pointsToWin = pointsToWin;
    myPlayers = new ArrayList<>();
    myDiscardPile = new CardPile();
    myDeck = new UnoDeck(version);
    currentPlayer = 0;
    this.playerMap = playerMap;

    try {
      myRules = createRules();
      myDrawRule = createDrawRule();
      createPlayers();
    } catch (Exception e) {
      e.printStackTrace();
    }

    this.version = version;
    this.playerMap = playerMap;
    this.stackable = stackable;

    uno = false;
    endGame = false;
    playerPoints = new int[myPlayers.size()];
  }

  /**
   * Default constructor for mocking purposes
   */
  public GameState() {
    order = 1;
    skipNext = false;
    impendingDraw = 0;
    this.pointsToWin = 100;
    myPlayers = new ArrayList<>();
    myDiscardPile = new CardPile();
    currentPlayer = 0;
  }

  /**
   * Used by the Load File feature
   * @param currentPlayer
   * @param myHands
   * @param myDiscardPile
   * @param myDeck
   * @param impendingDraw
   * @param skipNext
   * @param skipEveryone
   * @param order
   * @param playerPoints
   * @param uno
   */
  public void loadExistingGame(int currentPlayer, List<Hand> myHands, CardPile myDiscardPile, CardPile myDeck,
                               int impendingDraw, boolean skipNext, boolean skipEveryone, int order, int[] playerPoints,
                               boolean uno){
    this.currentPlayer = currentPlayer;
    this.myDiscardPile = myDiscardPile;
    this.myDeck = myDeck;
    this.impendingDraw = impendingDraw;
    this.skipNext = skipNext;
    this.skipEveryone = skipEveryone;
    this.order = order;
    this.playerPoints = playerPoints;
    this.uno = uno;

    for(int i = 0; i < myPlayers.size(); i++){
      myPlayers.get(i).loadHand(myHands.get(i));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getPlayerNames() {
    List<String> result = new ArrayList<>();
    for (Player p : myPlayers) {
      result.add(p.getName());
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Integer> getCardCounts() {
    List<Integer> result = new ArrayList<>();
    for (Player p : myPlayers) {
      result.add(p.getHandSize());
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CardPileViewInterface getDeck() {
    return myDeck;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CardPileViewInterface getDiscardPile() {
    return myDiscardPile;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void discardCard(CardInterface c) {
    myDiscardPile.placeOnTop(c);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLastCardThrownType() {
    return myDiscardPile.lastCardPushed().getType();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void reverseGamePlay() {
    order *= -1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void skipNextPlayer() {
    skipNext = true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void skipEveryone(){
    skipEveryone = true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void playTurn() {
    // FIXME: Add in stacking logic
    if(impendingDraw > myDeck.getNumCards()){
      myDiscardPile.copyOver(myDeck);
    }
    Player player = myPlayers.get(currentPlayer);
    player.playCard();
    if (uno) {
      int totalNumPoints = 0;
      for (Player p : myPlayers) {
        totalNumPoints += p.getNumPoints();
      }
      playerPoints[currentPlayer] += totalNumPoints;
      uno = false;
    }
    if(playerPoints[currentPlayer] >= pointsToWin){
      endGame = true;
    }
    loadNextPlayer();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getGameplayDirection() {
    return order;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addPlayer(Player p) {
    myPlayers.add(p);
    List<Integer> points = new ArrayList<Integer>();
    for(int i : playerPoints){
      points.add(i);
    }
    points.add(0);
    playerPoints = new int[points.size()];
    for(int i = 0; i < points.size(); i++){
      playerPoints[i] = points.get(i);
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ViewCardInterface> getCurrentPlayerCards() {
    return myPlayers.get(currentPlayer).getViewCards();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addDraw(int drawAmount) {
    impendingDraw += drawAmount;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCalledUno(boolean unoCalled) {
    uno = unoCalled;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void flipCards() {
    for (Player p : myPlayers){
      p.flipHand();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CardInterface getNextCard() {
    return myDeck.popTopCard();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<CardInterface> noPlayDraw() {
    if (impendingDraw == 0){
      return myDrawRule.noPlayDraw(this);
    }
    if (impendingDraw < 0){
      if (impendingDraw == -1){
        return myDrawRule.drawUntilBlast(this);
      }
      return myDrawRule.drawUntilColor(this, myDiscardPile.lastCardPushed().getMyColor());
    }
    int oldDraw = impendingDraw;
    impendingDraw = 0;
    return myDrawRule.forcedDraw(this, oldDraw);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canPlayCard(CardInterface cardToPlay) {
    return myRules.stream()
        .anyMatch(rule -> rule.canPlay(myDiscardPile.lastCardPushed(), cardToPlay, impendingDraw));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getOrder() {
    return order;
  }

  private void loadNextPlayer() {
    int boostedCurrentPlayer = currentPlayer + myPlayers.size();
    if (skipNext){
      currentPlayer = (boostedCurrentPlayer + 2 * order) % myPlayers.size();
      skipNext = false;
    } else if (skipEveryone){
      skipEveryone = false;
    } else {
      currentPlayer = (boostedCurrentPlayer + order) % myPlayers.size();
    }
  }

  /**
   * Used by the Save File feature
   * @return initial game parameter - version
   */
  public String getVersion() {
    return version;
  }

  /**
   * Used by the Save File feature
   * @return initial game parameter - map of player names to player type (human or CPU)
   */
  public Map<String, String> getPlayerMap() {
    return playerMap;
  }

  /**
   * Used by the Save File feature
   * @return initial game parameter - points required to win
   */
  public int getPointsToWin() {
    return pointsToWin;
  }

  /**
   * Used by the Save File feature
   * @return initial game parameter - boolean indicating stackable
   */
  public boolean getStackable() {
    return stackable;
  }

  /**
   * Checks whether two GameState objects have the same initial parameters - FOR TESTING PURPOSES ONLY
   *
   * @param other GameState object to compare this object with
   * @return boolean indicating whether the initial parameters are equal
   */
  public boolean compareInitialParameters(GameState other) {
    boolean condition1 = version.equals(other.getVersion());
    boolean condition2 = playerMap.equals(other.getPlayerMap());
    boolean condition3 = (pointsToWin == other.getPointsToWin());
    boolean condition4 = (stackable == other.getStackable());

    return condition1 && condition2 && condition3 && condition4;
  }

  /**
   * Used by the Save File feature
   * @return game in progress parameter - list of each Players' Hands
   */
  public List<Hand> getMyHands() {
    List<Hand> myHands = new ArrayList<>();
    for(Player player : myPlayers){
      myHands.add(player.getMyHand());
    }
    return myHands;
  }

  /**
   * Used by the Save File feature
   * @return game in progress parameter - discard pile
   */
  public CardPile getMyDiscardPile() {
    return myDiscardPile;
  }

  /**
   * Used by the Save File feature
   * @return game in progress parameter - deck
   */
  public CardPile getMyDeck() {
    return myDeck;
  }

  /**
   * Used by the Save File feature
   * @return game in progress parameter - impending draw number
   */
  public int getImpendingDraw() {
    return impendingDraw;
  }

  /**
   * Used by the Save File feature
   * @return game in progress parameter - boolean indicating skip next player
   */
  public boolean getSkipNext() {
    return skipNext;
  }

  /**
   * Used by the Save File feature
   * @return game in progress parameter - boolean indicating skip every player
   */
  public boolean getSkipEveryone() {
    return skipEveryone;
  }

  /**
   * Used by the Save File feature
   * @return game in progress parameter - array of players' points
   */
  public int[] getPlayerPoints() {
    return playerPoints;
  }

  /**
   * Used by the Save File feature
   * @return game in progress parameter - boolean indicating UNO has been reached
   */
  public boolean getUno() {
    return uno;
  }

  // Creates the list of players based on the map that's passed into the constructor
  @Override
  @Deprecated
  public void createPlayers(Supplier<Integer> integerSupplier, Supplier<String> stringSupplier)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    for (String name : playerMap.keySet()) {
      Class<?> playerClass = Class.forName(
          String.format(gameStateResources.getString("PlayerClassBase"),
              gameStateResources.getString(playerMap.get(name))));
      Player player = (Player) playerClass.getDeclaredConstructor(String.class,
          GameStatePlayerInterface.class, Supplier.class, Supplier.class).newInstance(name, this, integerSupplier, stringSupplier);
      myPlayers.add(player);
    }
    dealCards();
    myDiscardPile.placeOnTop(myDeck.popTopCard());
  }

  private void createPlayers()
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    for (String name : playerMap.keySet()) {
      Class<?> playerClass = Class.forName(
          String.format(gameStateResources.getString("PlayerClassBase"),
              gameStateResources.getString(playerMap.get(name))));
      Player player = (Player) playerClass.getDeclaredConstructor(String.class,
          GameStatePlayerInterface.class).newInstance(name, this);
      myPlayers.add(player);
    }
    dealCards();
    myDiscardPile.placeOnTop(myDeck.popTopCard());
  }


  // Creates the list of players based on the map that's passed into the constructor
  @Override
  @Deprecated
  public void createPlayers(Supplier<Integer> integerSupplier)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    for (String name : playerMap.keySet()) {
      Class<?> playerClass = Class.forName(
          String.format(gameStateResources.getString("PlayerClassBase"),
              gameStateResources.getString(playerMap.get(name))));
      Player player = (Player) playerClass.getDeclaredConstructor(String.class,
          GameStatePlayerInterface.class, Supplier.class, Supplier.class).newInstance(name, this, integerSupplier, null);
      myPlayers.add(player);
    }
    dealCards();
    myDiscardPile.placeOnTop(myDeck.popTopCard());
  }

  @Override
  public void setSuppliers(Supplier<Integer> integerSupplier, Supplier<String> stringSupplier) {
    for (PlayerInterface p : myPlayers){
      p.setSuppliers(integerSupplier, stringSupplier);
    }
  }


  private DrawRuleInterface createDrawRule()
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    Class<?> clazz = Class.forName(String.format(gameStateResources.getString("DrawRuleBase"),
        gameStateResources.getString("DrawRule")));
    return (DrawRuleInterface) clazz.getDeclaredConstructor().newInstance();
  }

  private List<RuleInterface> createRules()
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    String base = gameStateResources.getString("PlayRulesBase");
    List<RuleInterface> ret = new ArrayList<>();
    for (String rule : gameStateResources.getString("PlayRules").split(",")) {
      Class<?> clazz = Class.forName(String.format(base, rule));
      ret.add((RuleInterface) clazz.getDeclaredConstructor().newInstance());
    }
    return ret;
  }

  private void dealCards() {

    for (int i = 0; i < NUM_CARDS_PER_PLAYER; i++) {
      for (Player myPlayer : myPlayers) {
        CardInterface newCard = myDeck.popTopCard();
        myPlayer.addCards(List.of(newCard));
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Deprecated
  public void createDeck(Map<String, Supplier<String>> map){
    // Do nothing
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean getEndGame() {
    return endGame;
  }
}