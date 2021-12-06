package ooga.model.gameState;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import java.util.function.Supplier;
import ooga.model.cards.CardInterface;
import ooga.model.cards.ViewCardInterface;
import ooga.model.deck.CardPile;
import ooga.model.deck.CardPileViewInterface;
import ooga.model.deck.DeckWrapper;
import ooga.model.deck.UnoDeck;
import ooga.model.drawRule.DrawRuleInterface;
import ooga.model.hand.Hand;
import ooga.model.instanceCreation.ReflectionErrorException;
import ooga.model.instanceCreation.ReflectionHandler;
import ooga.model.instanceCreation.ReflectionHandlerInterface;
import ooga.model.player.player.Player;

import ooga.model.player.player.PlayerGameInterface;
import ooga.model.player.playerGroup.PlayerGroup;
import ooga.model.player.playerGroup.PlayerGroupGameInterface;
import ooga.model.player.player.ViewPlayerInterface;
import ooga.model.rules.RuleInterface;
import ooga.model.rules.RuleSet;

public class GameState implements GameStateInterface, GameStateViewInterface,
    GameStatePlayerInterface, GameStateDrawInterface {

  private static final String BUNDLE_PATH = "ooga.model.gameState.GameStateResources";
  private static final String RULES_PATH = "RulesBase";
  private static final String CARDS_PER_PLAYER = "CardsPerPlayer";
  private static final String POINTS_TO_WIN = "DefaultPointsToWin";
  private static final String GAME_TYPE = "DefaultGameType";

  private static final ResourceBundle gameStateResources = ResourceBundle.getBundle(BUNDLE_PATH);

  private ResourceBundle ruleResources;

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
    } catch (Exception e) {
      e.printStackTrace();
    }

    ruleResources = ResourceBundle.getBundle(
        String.format(gameStateResources.getString(RULES_PATH), version));

    try {
      myRules = new RuleSet(version, stackable);
      myDrawRule = createDrawRule();
    } catch (Exception e) {
      e.printStackTrace();
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
    this.pointsToWin = Integer.parseInt(POINTS_TO_WIN);
    cardContainer = new DeckWrapper(new UnoDeck(GAME_TYPE), new CardPile());
    cardPerPlayer = Integer.parseInt(gameStateResources.getString(CARDS_PER_PLAYER));
    try {
      myPlayerGroup = new PlayerGroup(new HashMap<>(), this);
    } catch (Exception e) {
      e.printStackTrace();
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
    myPlayerGroup.setCurrent(currentPlayer);
    UnoDeck newDeck = new UnoDeck(version);
    newDeck.setPile(myDeck.getStack());
    this.cardContainer = new DeckWrapper(newDeck, myDiscardPile);
    this.impendingDraw = impendingDraw;
    myPlayerGroup.setOrder(order);
    myPlayerGroup.loadHands(myHands);
  }

  @Override
  public List<ViewPlayerInterface> getPlayers() {
    List<ViewPlayerInterface> players = new ArrayList<>();
    for (PlayerGameInterface p : myPlayerGroup) {
      players.add((ViewPlayerInterface) p);
    }
    return players;
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
    myPlayerGroup.playTurn();
    PlayerGameInterface player = myPlayerGroup.getCurrentPlayer();
    if (player.getHandSize() == 0) {
      myPlayerGroup.countAndAwardPoints();
      endGame = player.getNumPoints() >= pointsToWin;
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
    return 0; // Deprecated
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
  public void flipCards() {
    // Do Nothing (Deprecated)
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<CardInterface> getUnoPunishment() {
    return myDrawRule.forcedDraw(this, 2);
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
  public Collection<CardInterface> noPlayDraw() {
    if (impendingDraw == 0) {
      return myDrawRule.noPlayDraw(this);
    }
    if (impendingDraw < 0) {
      if (impendingDraw == -1) {
        return myDrawRule.drawUntilBlast(this);
      }
      return myDrawRule.drawUntilColor(this, cardContainer.getLastCard().getMyColor());
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

    return condition1 && condition2 && condition3 && condition4;
  }

  public Collection<ViewCardInterface> getBlasterCards() {
    return myDrawRule.getBlasterCards();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public void createPlayers(Supplier<Integer> integerSupplier, Supplier<String> stringSupplier)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    // Do Nothing, Deprecate
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public void createPlayers(Supplier<Integer> integerSupplier)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
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

  public void setCalledUno(boolean uno) {
    myPlayerGroup.setUnoCalled(uno);
  }

  private DrawRuleInterface createDrawRule() throws ReflectionErrorException {
    ReflectionHandlerInterface ref = new ReflectionHandler();
    return ref.getDrawRule(ruleResources.getString("DrawRule"));
  }

  private boolean comparePlayerHands(GameState other) {
    List<Hand> hands = getMyHands();
    for (int i = 0; i < hands.size(); i++) {
      Hand thisHand = this.getMyHands().get(i);
      List<CardInterface> thisHandCards = thisHand.getMyCards();
      Hand otherHand = other.getMyHands().get(i);
      List<CardInterface> otherHandCards = otherHand.getMyCards();
      if (!thisHandCards.equals(otherHandCards)) {
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
}