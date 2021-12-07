package ooga.model.gameState;

import java.lang.invoke.MethodHandles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Supplier;
import java.util.logging.Level;
import ooga.model.cards.CardInterface;
import ooga.model.cards.ViewCardInterface;
import ooga.model.deck.CardPile;
import ooga.model.deck.CardPileViewInterface;
import ooga.model.deck.DeckWrapper;
import ooga.model.deck.UnoDeck;
import ooga.model.drawRule.DrawRuleInterface;
import ooga.model.hand.Hand;
import ooga.model.instanceCreation.ReflectionErrorException;
import ooga.model.instanceCreation.ReflectionHandlerInterface;
import ooga.model.player.player.Player;

import ooga.model.player.player.PlayerGameInterface;
import ooga.model.player.playerGroup.PlayerGroup;
import ooga.model.player.playerGroup.PlayerGroupGameInterface;
import ooga.model.player.player.ViewPlayerInterface;
import ooga.model.rules.RuleInterface;
import ooga.model.rules.RuleSet;
import ooga.util.Log;

public class GameState implements GameStateInterface, GameStateViewInterface,
    GameStatePlayerInterface, GameStateDrawInterface {

  private static final String BUNDLE_PATH = "ooga.model.gameState.resources.GameStateResources";
  private static final String CARDS_PER_PLAYER = "CardsPerPlayer";
  private static final String POINTS_TO_WIN = "DefaultPointsToWin";
  private static final String GAME_TYPE = "DefaultGameType";
  private static final String DRAW_RULE_BASE = "DrawRuleFormat";
  private static final String CHEAT_KEYS = "CheatKeys";
  private static final String DRAW_BASE = "DrawFormat";
  private static final String DIVIDER = "Divider";
  private static final String UNO_PUNISHMENT = "Punishment";

  private static final ResourceBundle gameStateResources = ResourceBundle.getBundle(BUNDLE_PATH);
  private static final String LOG_FILE = ".\\data\\logMessages.txt";

  private DeckWrapper cardContainer;
  private int impendingDraw;

  private String version;
  private RuleInterface myRules;
  private DrawRuleInterface myDrawRule;
  private boolean stackable;
  private final int pointsToWin;
  private PlayerGroupGameInterface myPlayerGroup;

  private boolean endGame;
  private final int cardPerPlayer;

  public boolean loadedGameInProgress = false; // Used by UnoController

  public GameState(String version, Map<String, String> playerMap, int pointsToWin,
      boolean stackable) {
    impendingDraw = 0;
    this.pointsToWin = pointsToWin;
    cardContainer = new DeckWrapper(new UnoDeck(version), new CardPile());
    this.version = gameStateResources.getString(version);
    cardPerPlayer = Integer.parseInt(gameStateResources.getString(CARDS_PER_PLAYER));
    this.stackable = stackable;
    try {
      myPlayerGroup = new PlayerGroup(playerMap, this);
      myRules = new RuleSet(version, stackable);
      myDrawRule = createDrawRule();
    } catch (Exception e) {
      logError(e.getMessage());
    }
    dealCards();
    cardContainer.discard(cardContainer.draw());
    endGame = false;
  }

  /**
   * Default constructor for mocking purposes
   */
  public GameState() {
    impendingDraw = 0;
    this.pointsToWin = Integer.parseInt(gameStateResources.getString(POINTS_TO_WIN));
    cardContainer = new DeckWrapper(new UnoDeck(gameStateResources.getString(GAME_TYPE)),
        new CardPile());
    cardPerPlayer = Integer.parseInt(gameStateResources.getString(CARDS_PER_PLAYER));
    try {
      myPlayerGroup = new PlayerGroup(new HashMap<>(), this);
    } catch (Exception e) {
      logError(e.getMessage());
    }
  }

  /**
   * Used by the Load File feature
   *
   * @param currentPlayer
   * @param myHands
   * @param myDiscardPile
   * @param impendingDraw
   * @param order
   */
  public void loadExistingGame(int currentPlayer, List<Hand> myHands, CardPile myDiscardPile,
      CardPile myDeck, int impendingDraw, int order) {
    loadedGameInProgress = true;
    myPlayerGroup.setCurrent(currentPlayer);
    UnoDeck newDeck = new UnoDeck(version);
    newDeck.setPile(myDeck.getStack());
    this.cardContainer = new DeckWrapper(newDeck, myDiscardPile);
    this.impendingDraw = impendingDraw;
    myPlayerGroup.setOrder(order);
    myPlayerGroup.loadHands(myHands);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ViewPlayerInterface> getPlayers() {
    List<ViewPlayerInterface> players = new ArrayList<>();
    for (PlayerGameInterface p : myPlayerGroup) {
      players.add((ViewPlayerInterface) p);
    }
    return players;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getGameplayDirection() {
    return myPlayerGroup.getMyOrder();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CardPileViewInterface getDeck() {
    return (CardPileViewInterface) cardContainer.getDeck();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CardPileViewInterface getDiscardPile() {
    return (CardPileViewInterface) cardContainer.getDiscardPile();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void discardCard(CardInterface c) {
    cardContainer.discard(c);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLastCardThrownType() {
    return cardContainer.getLastCard().getType();
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
    try {
      myPlayerGroup.playTurn();
    } catch (Exception e) {
      logError(e.getMessage());
    }
    PlayerGameInterface player = myPlayerGroup.getCurrentPlayer();
    if (player.getHandSize() == 0) {
      myPlayerGroup.countAndAwardPoints();
      endGame = player.getPoints() >= pointsToWin;
      for (PlayerGameInterface p : myPlayerGroup) {
        p.dumpCards();
      }
      cardContainer = new DeckWrapper(new UnoDeck(version), new CardPile());
      cardContainer.discard(cardContainer.getTopCard());
      dealCards();
    } else {
      myPlayerGroup.loadNextPlayer();
    }
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
    return myPlayerGroup.getMyOrder();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getCurrentPlayer() {
    return myPlayerGroup.getCurrentPlayerIndex();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ViewCardInterface> getCurrentPlayerCards() {
    return myPlayerGroup.getCurrentPlayer().getViewCards();
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
  public Collection<CardInterface> getUnoPunishment() {
    return myDrawRule.forcedDraw(this,
        Integer.parseInt(gameStateResources.getString(UNO_PUNISHMENT)));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CardInterface getNextCard() {
    return cardContainer.getTopCard();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<CardInterface> noPlayDraw() throws ReflectionErrorException {
    int oldDraw = impendingDraw;
    impendingDraw = 0;
    if (oldDraw == 0) {
      return myDrawRule.noPlayDraw(this);
    } else if (oldDraw < 0) {
      return ReflectionHandlerInterface.performSpecialDraw(
          gameStateResources.getString(String.format(gameStateResources.getString(DRAW_BASE),
              oldDraw)), this,
          cardContainer.getLastCard().getMyColor(), myDrawRule);
    }
    return myDrawRule.forcedDraw(this, oldDraw);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canPlayCard(CardInterface cardToPlay) {
    return myRules.canPlay(cardContainer.peekTopDiscard(), cardToPlay, impendingDraw);
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
    List<Hand> hands = new ArrayList<>();
    for (PlayerGameInterface player : myPlayerGroup) {
      hands.add(player.getMyHand());
    }
    return hands;
  }

  /**
   * Used by the Save File feature
   *
   * @return game in progress parameter - discard pile
   */
  public CardPile getMyDiscardPile() {
    return (CardPile) cardContainer.getDiscardPile();
  }

  /**
   * Used by the Save File feature
   *
   * @return game in progress parameter - deck
   */
  public CardPile getMyDeck() {
    return (CardPile) cardContainer.getDeck();
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
   * Checks whether two GameState objects have the same initial parameters - TESTING PURPOSES ONLY
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

  /**
   * Checks whether two GameState objects have the same game in progress parameters - TESTING
   * PURPOSES ONLY
   *
   * @param other GameState object to compare this object with
   * @return boolean indicating whether the GameState's are equal
   */
  public boolean compareGameInProgressParameters(GameState other) {
    boolean condition1 = compareInitialParameters(other);
    boolean condition2 = comparePlayerHands(other);
    boolean condition3 = other.getMyDeck().getStack().equals(this.getMyDeck().getStack());
    boolean condition4 = other.getMyDiscardPile().getStack()
        .equals(this.getMyDiscardPile().getStack());
    boolean condition5 = true;
    if (myDrawRule.getBlasterList() != null) {
      condition5 = this.getBlasterList().equals(other.getBlasterList());
    }

    return condition1 && condition2 && condition3 && condition4 && condition5;
  }

  /**
   * @return View Approved version of all cards in the blaster
   */
  public Collection<ViewCardInterface> getBlasterCards() {
    return myDrawRule.getBlasterCards();
  }

  /**
   * @return actual version of all cards in the blaster - used by the Save File feature
   */
  public List<CardInterface> getBlasterList() {
    return myDrawRule.getBlasterList();
  }

  /**
   * sets the cards in the blaster - used by the Load File feature
   */
  public void loadBlaster(List<CardInterface> cards) {
    myDrawRule.loadBlaster(cards);
  }

  @Override
  public boolean blasterWentOff() {
    return myDrawRule.blasted();
  }

  @Override
  public void cheatKey(char key) {
    try {
      if (gameStateResources.getString(CHEAT_KEYS).indexOf(key) >= 0) {
        List<String> methodAndArgs = List.of(
            gameStateResources.getString(String.valueOf(key))
                .split(gameStateResources.getString(DIVIDER)));
        ReflectionHandlerInterface.performCheatMethod(methodAndArgs.get(0), methodAndArgs.get(1),
            methodAndArgs.get(2), myPlayerGroup);
      }
    } catch (Exception e) {
      logError(e.getMessage());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public void createPlayers(Supplier<Integer> integerSupplier, Supplier<String> stringSupplier) {
    // Do Nothing, Deprecate
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public void createPlayers(Supplier<Integer> integerSupplier) {
    // Do Nothing, Deprecated
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSuppliers(Supplier<Integer> integerSupplier, Supplier<String> stringSupplier) {
    for (PlayerGameInterface p : myPlayerGroup) {
      p.setSuppliers(integerSupplier, stringSupplier);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean userPicksCard() {
    return myPlayerGroup.userPicksCard();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<Integer> getValidIndexes() {
    return myPlayerGroup.getCurrentPlayer().getValidIndexes();
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

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCalledUno(boolean uno) {
    myPlayerGroup.setUnoCalled(uno);
  }

  private DrawRuleInterface createDrawRule() throws ReflectionErrorException {
    return ReflectionHandlerInterface.getDrawRule(gameStateResources.getString(
        String.format(gameStateResources.getString(DRAW_RULE_BASE), version)));
  }

  private boolean comparePlayerHands(GameState other) {
    List<Hand> hands = this.getMyHands();
    List<Hand> otherHands = other.getMyHands();
    for (int i = 0; i < hands.size(); i++) {
      Hand currentHand = hands.get(i);
      List<CardInterface> currentHandCards = currentHand.getMyCards();
      Hand currentOtherHand = otherHands.get(i);
      List<CardInterface> otherHandCards = currentOtherHand.getMyCards();
      if (!currentHandCards.equals(otherHandCards)) {
        return false;
      }
    }
    return true;
  }

  private void dealCards() {
    for (int i = 0; i < cardPerPlayer; i++) {
      for (PlayerGameInterface player : myPlayerGroup) {
        CardInterface newCard = cardContainer.draw();
        player.addCards(List.of(newCard));
      }
    }
  }

  private void logError(String message) {
    try {
      Log log = new Log(LOG_FILE, MethodHandles.lookup().lookupClass().toString());
      log.getLogger().setLevel(Level.WARNING);
      log.getLogger().warning(message);
    } catch (Exception ignored) {

    }
  }
}