package ooga.model.gameState;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import ooga.model.cards.CardInterface;
import ooga.model.cards.ViewCardInterface;
import ooga.model.deck.CardPile;
import ooga.model.deck.CardPileViewInterface;
import ooga.model.deck.UnoDeck;
import ooga.model.drawRule.DrawRuleInterface;
import ooga.model.player.Player;

import ooga.model.cards.Card;
import ooga.model.rules.RuleInterface;

public class GameState implements GameStateInterface, GameStateViewInterface,
    GameStatePlayerInterface, GameStateDrawInterface {

  private final ResourceBundle gameStateResources = ResourceBundle.getBundle(
      "ooga.model.gameState.GameStateResources");

  private int currentPlayer;
  private final List<Player> myPlayers;
  private final CardPile myDiscardPile;
  private CardPile myDeck;

  private int impendingDraw;
  private boolean skipNext;
  private int order;

  private String version;
  private List<RuleInterface> myRules;
  private DrawRuleInterface myDrawRule;
  private Map<String, String> playerMap;
  private int[] playerPoints;
  private boolean stackable;
  private final int pointsToWin;

  private boolean uno;
  private final static int NUM_CARDS_PER_PLAYER = 7;


  public GameState(String version, Map<String, String> playerMap, int pointsToWin,
      boolean stackable) {
    order = 1;
    skipNext = false;
    impendingDraw = 0;
    this.pointsToWin = pointsToWin;
    myPlayers = new ArrayList<>();
    myDiscardPile = new CardPile();
    currentPlayer = 0;

    try {
      myRules = createRules();
      myDrawRule = createDrawRule();
    } catch (Exception e) {
      e.printStackTrace();
    }

    // FIXME: Create useful error
    try {
      createPlayers(playerMap);
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.version = version;
    this.playerMap = playerMap;
    this.stackable = stackable;

    uno = false;
    playerPoints = new int[myPlayers.size()];

    myDeck = new UnoDeck(version);
    dealCards();
    myDiscardPile.placeOnTop(myDeck.popTopCard());
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

  @Override
  public List<String> getPlayerNames() {
    List<String> result = new ArrayList<>();
    for (Player p : myPlayers) {
      result.add(p.getName());
    }
    return result;
  }

  @Override
  public List<Integer> getCardCounts() {
    List<Integer> result = new ArrayList<>();
    for (Player p : myPlayers) {
      result.add(p.getHandSize());
    }
    return result;
  }

  @Override
  public CardPileViewInterface getDeck() {
    return myDeck;
  }

  @Override
  public CardPileViewInterface getDiscardPile() {
    return myDiscardPile;
  }

  @Override
  public void discardCard(Card c) {
    myDiscardPile.placeOnTop(c);
  }

  @Override
  public String getLastCardThrownType() {
    return myDiscardPile.lastCardPushed().getType();
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
    // FIXME: Add in stacking logic
    Player player = myPlayers.get(currentPlayer);
    if (impendingDraw > 0) {
      myDrawRule.forcedDraw(this, impendingDraw);
      impendingDraw = 0;
    } else {
      player.playCard();
    }
    if (uno) {
      int totalNumPoints = 0;
      for (Player p : myPlayers) {
        totalNumPoints += p.getNumPoints();
      }
      playerPoints[currentPlayer] += totalNumPoints;
      uno = false;
    }
    loadNextPlayer();
  }

  @Override
  public int getGameplayDirection() {
    return order;
  }

  @Override
  public void addPlayer(Player p) {
    myPlayers.add(p);
  }

  @Override
  public int getCurrentPlayer() {
    return currentPlayer;
  }

  @Override
  public List<ViewCardInterface> getCurrentPlayerCards() {
    return myPlayers.get(currentPlayer).getViewCards();
  }

  @Override
  public void addDraw(int drawAmount) {
    impendingDraw += drawAmount;
  }

  @Override
  public CardInterface getNextCard() {
    return myDeck.popTopCard();
  }

  @Override
  public Collection<CardInterface> noPlayDraw() {
    return myDrawRule.noPlayDraw(this);
  }

  @Override
  public boolean canPlayCard(CardInterface cardToPlay) {
    return myRules.stream()
        .anyMatch(rule -> rule.canPlay(myDiscardPile.lastCardPushed(), cardToPlay));
  }

  @Override
  public int getOrder() {
    return order;
  }

  private void loadNextPlayer() {
    int boostedCurrentPlayer = currentPlayer + myPlayers.size();
    if (!skipNext) {
      currentPlayer = (boostedCurrentPlayer + order) % myPlayers.size();
    } else {
      currentPlayer = (boostedCurrentPlayer + 2 * order) % myPlayers.size();
      skipNext = false;
    }
  }

  /**
   * @return game version
   */
  public String getVersion() {
    return version;
  }

  /**
   * @return map of player names to player type (human or CPU)
   */
  public Map<String, String> getPlayerMap() {
    return playerMap;
  }

  /**
   * @return points required to win
   */
  public int getPointsToWin() {
    return pointsToWin;
  }

  /**
   * @return boolean indicating stackable
   */
  public boolean getStackable() {
    return stackable;
  }

  /**
   * Tests whether two GameState objects have the same initial parameters
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

  // Creates the list of players based on the map that's passed into the constructor
  private void createPlayers(Map<String, String> playerMap)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    for (String name : playerMap.keySet()) {
      Class<?> playerClass = Class.forName(
          String.format(gameStateResources.getString("PlayerClassBase"),
              gameStateResources.getString(playerMap.get(name))));
      Player player = (Player) playerClass.getDeclaredConstructor(String.class,
          GameStatePlayerInterface.class).newInstance(name, this);
      myPlayers.add(player);
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
        Card newCard = myDeck.popTopCard();
        myPlayer.addCards(List.of(newCard));
      }
    }
  }

}