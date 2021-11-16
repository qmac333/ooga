package ooga.model.gameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import ooga.model.player.Player;

import ooga.model.cards.Card;
import ooga.model.rules.RuleInterface;

public class GameState implements GameStateInterface, GameStateViewInterface,
    GameStatePlayerInterface {

  private int order;
  private int currentPlayer;
  private List<Player> players;
  private Stack<Card> discardPile;
  private List<RuleInterface> myRules;
  private boolean currentPlayerPlayCard;
  private final int pointsToWin;

  private int cardNumConstraint;
  private String cardColorConstraint;

  private int impendingDraw;

  private boolean skipNext;

  private String version;
  private Map<String, String> playerMap;
  private boolean stackable;

  public GameState(String version, Map<String, String> playerMap, int pointsToWin,
      boolean stackable) {
    order = 1;
    skipNext = false;
    impendingDraw = 0;
    this.pointsToWin = pointsToWin;
    players = new ArrayList<>();
    discardPile = new Stack<>();
    currentPlayerPlayCard = false;
    currentPlayer = 0;
    myRules = new ArrayList<>();

    this.version = version;
    this.playerMap = playerMap;
    this.stackable = stackable;
  }

  /**
   * Default constructor for mocking purposes
   */
  public GameState() {
    order = 1;
    skipNext = false;
    impendingDraw = 0;
    this.pointsToWin = 100;
    players = new ArrayList<>();
    discardPile = new Stack<>();
    currentPlayerPlayCard = false;
    currentPlayer = 0;
  }

  @Override
  public List<String> getPlayerNames() {
    List<String> result = new ArrayList<>();
    for (Player p : players) {
      result.add(p.getName());
    }
    return result;
  }

  @Override
  public List<Integer> getCardCounts() {
    List<Integer> result = new ArrayList<>();
    for (Player p : players) {
      result.add(p.getHand().size());
    }
    return result;
  }

  @Override
  public void discardCard(Card c) {
    discardPile.push(c);
    cardColorConstraint = discardPile.peek().getMyColor();
    cardNumConstraint = discardPile.peek().getNum();
  }


  @Override
  public String getLastCardThrownType() {
    return discardPile.peek().getType();
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
    Player player = players.get(currentPlayer);
    if (impendingDraw > 0) {
      enforceDrawRule(player);
    } else {
      player.playCard();
    }
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
  public void addDraw(int drawAmount) {
    impendingDraw += drawAmount;
  }

  @Override
  public Card getNextCard() {
    return null;
  }

  @Override
  public boolean canPlayCard(Card cardToPlay) {
    return myRules.stream().anyMatch(rule -> rule.canPlay(discardPile.peek(), cardToPlay));
  }

  @Override
  public int getOrder() {
    return order;
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

  private void enforceDrawRule(Player player) {
    while (impendingDraw > 0) {
      player.addCard(getNextCard());
      impendingDraw--;
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
}