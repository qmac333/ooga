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

import ooga.model.player.PlayerGroup;
import ooga.model.player.PlayerGroupInterface;
import ooga.model.player.ViewPlayerInterface;
import ooga.model.rules.RuleInterface;

public class GameState implements GameStateInterface, GameStateViewInterface,
    GameStatePlayerInterface, GameStateDrawInterface {

  private final ResourceBundle gameStateResources = ResourceBundle.getBundle(
      "ooga.model.gameState.GameStateResources");
  private ResourceBundle ruleResources;

  private CardPile myDiscardPile;
  private CardPile myDeck;

  private int impendingDraw;

  private String version;
  private List<RuleInterface> myRules;
  private DrawRuleInterface myDrawRule;
  private boolean stackable;
  private final int pointsToWin;
  private PlayerGroupInterface myPlayerGroup;


  private boolean uno;
  private boolean endGame;
  private final static int NUM_CARDS_PER_PLAYER = 7;


  public GameState(String version, Map<String, String> playerMap, int pointsToWin,
      boolean stackable) {
    impendingDraw = 0;
    this.pointsToWin = pointsToWin;
    myDiscardPile = new CardPile();
    myDeck = new UnoDeck(version);
    this.version = gameStateResources.getString(version);
    this.stackable = stackable;
    try {
      myPlayerGroup = new PlayerGroup(playerMap, this);
    } catch(Exception e){
      e.printStackTrace();
    }

    ruleResources = ResourceBundle.getBundle(
        String.format(gameStateResources.getString("RulesBase"), version));

    try {
      myRules = createRules();
      myDrawRule = createDrawRule();
    } catch (Exception e) {
      e.printStackTrace();
    }
    myPlayerGroup.dealCards(myDeck, NUM_CARDS_PER_PLAYER);
    myDiscardPile.placeOnTop(myDeck.popTopCard());
    uno = false;
    endGame = false;
  }

  /**
   * Default constructor for mocking purposes
   */
  public GameState() {
    impendingDraw = 0;
    this.pointsToWin = 100;
    myDiscardPile = new CardPile();
    try {
      myPlayerGroup = new PlayerGroup(new HashMap<>(), this);
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  /**
   * Used by the Load File feature
   *
   * @param currentPlayer
   * @param myHands
   * @param myDiscardPile
   * @param myDeck
   * @param impendingDraw
   * @param order
   */
  public void loadExistingGame(int currentPlayer, List<Hand> myHands, CardPile myDiscardPile,
      CardPile myDeck, int impendingDraw, int order) {
    this.myDiscardPile = myDiscardPile;
    this.myDeck = myDeck;
    this.impendingDraw = impendingDraw;
    myPlayerGroup.setOrder(order);
    this.uno = uno;
    myPlayerGroup.loadHands(myHands);
  }

  @Override
  public List<ViewPlayerInterface> getPlayers() {
    return myPlayerGroup.getViewPlayers();
  }

  @Override
  public int getGameplayDirection() {
    return myPlayerGroup.getMyOrder();
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
    // Do Nothing, Deprecated
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void skipNextPlayer() {
    // Do Nothing, Deprecated
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void skipEveryone() {
    // Do Nothing, Deprecated
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void playTurn() {
    myPlayerGroup.playTurn();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addPlayer(Player p) {
    myPlayerGroup.addPlayer(p);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getOrder() {
    return 0; // Deprecated
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getCurrentPlayer() {
    return myPlayerGroup.getCurrentPlayer();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ViewCardInterface> getCurrentPlayerCards() {
    return myPlayerGroup.getCurrentPlayerCards();
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
    // Do Nothing (Deprecated)
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
    if (impendingDraw == 0) {
      return myDrawRule.noPlayDraw(this);
    }
    if (impendingDraw < 0) {
      if (impendingDraw == -1) {
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
   * Used by the Save File feature
   *
   * @return initial game parameter - version
   */
  public String getVersion() {
    return version;
  }

  /**
   * Used by the Save File feature
   *
   * @return initial game parameter - map of player names to player type (human or CPU)
   */
  public Map<String, String> getPlayerMap() {
    return myPlayerGroup.getPlayerMap();
  }

  /**
   * Used by the Save File feature
   *
   * @return initial game parameter - points required to win
   */
  public int getPointsToWin() {
    return pointsToWin;
  }

  /**
   * Used by the Save File feature
   *
   * @return initial game parameter - boolean indicating stackable
   */
  public boolean getStackable() {
    return stackable;
  }

  /**
   * Used by the Save File feature
   *
   * @return game in progress parameter - list of each Players' Hands
   */
  public List<Hand> getMyHands() {
    return myPlayerGroup.getHands();
  }

  /**
   * Used by the Save File feature
   *
   * @return game in progress parameter - discard pile
   */
  public CardPile getMyDiscardPile() {
    return myDiscardPile;
  }

  /**
   * Used by the Save File feature
   *
   * @return game in progress parameter - deck
   */
  public CardPile getMyDeck() {
    return myDeck;
  }

  /**
   * Used by the Save File feature
   *
   * @return game in progress parameter - impending draw number
   */
  public int getImpendingDraw() {
    return impendingDraw;
  }

  /**
   * Checks whether two GameState objects have the same initial parameters - FOR TESTING PURPOSES ONLY
   *
   * @param other GameState object to compare this object with
   * @return boolean indicating whether the initial parameters are equal
   */
  public boolean compareInitialParameters(GameState other) {
    boolean condition1 = version.equals(other.getVersion());
    boolean condition2 = myPlayerGroup.getPlayerMap().equals(other.getPlayerMap());
    boolean condition3 = (pointsToWin == other.getPointsToWin());
    boolean condition4 = (stackable == other.getStackable());

    return condition1 && condition2 && condition3 && condition4;
  }

  // Creates the list of players based on the map that's passed into the constructor
  @Override
  @Deprecated
  public void createPlayers(Supplier<Integer> integerSupplier, Supplier<String> stringSupplier)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    // Do Nothing, Deprecated
  }


  // Creates the list of players based on the map that's passed into the constructor
  @Override
  @Deprecated
  public void createPlayers(Supplier<Integer> integerSupplier)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    // Do Nothing, Deprecated
  }

  @Override
  public void setSuppliers(Supplier<Integer> integerSupplier, Supplier<String> stringSupplier) {
    myPlayerGroup.setSuppliers(integerSupplier, stringSupplier);
  }

  @Override
  public boolean userPicksCard() {
    return myPlayerGroup.userPicksCard();
  }

  @Override
  public Collection<Integer> getValidIndexes() {
    return myPlayerGroup.getCurrentPlayerValidIndexes();
  }


  private DrawRuleInterface createDrawRule()
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    Class<?> clazz = Class.forName(String.format(ruleResources.getString("DrawRuleBase"),
        ruleResources.getString("DrawRule")));
    return (DrawRuleInterface) clazz.getDeclaredConstructor().newInstance();
  }

  private List<RuleInterface> createRules()
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    String base = ruleResources.getString("PlayRulesBase");
    List<RuleInterface> ret = new ArrayList<>();
    for (String rule : ruleResources.getString("PlayRules").split(",")) {
      Class<?> clazz = Class.forName(String.format(base, rule));
      ret.add((RuleInterface) clazz.getDeclaredConstructor().newInstance());
    }
    if (stackable){
      Class<?> clazz = Class.forName(String.format(base, ruleResources.getString("StackingRule")));
      ret.add((RuleInterface) clazz.getDeclaredConstructor().newInstance());
    }
    return ret;
  }

  /**
   * {@inheritDoc}
   */
  @Deprecated
  public void createDeck(Map<String, Supplier<String>> map) {
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