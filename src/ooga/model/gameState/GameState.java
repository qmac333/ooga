package ooga.model.gameState;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import ooga.model.CardFactory;
import ooga.model.cards.ViewCardInterface;
import ooga.model.deck.CardPile;
import ooga.model.deck.CardPileInterface;
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
  private final Stack<Card> myDiscardPile;
  private CardPile myDeck;

  private int impendingDraw;
  private boolean skipNext;
  private int order;

  private String version;
  private List<RuleInterface> myRules;
  private DrawRuleInterface myDrawRule;
  private Map<String, String> playerMap;
  private boolean stackable;
  private final int pointsToWin;

  private CardFactory myCardFactory;

  public GameState(String version, Map<String, String> playerMap, int pointsToWin,
      boolean stackable) {
    order = 1;
    skipNext = false;
    impendingDraw = 0;
    this.pointsToWin = pointsToWin;
    myPlayers = new ArrayList<>();
    myDiscardPile = new Stack<>();
    currentPlayer = 0;


    try {
      myRules = createRules();
      myDrawRule = createDrawRule();
    } catch (Exception e){
      e.printStackTrace();
    }


    myCardFactory = new CardFactory();
    myDeck = new Stack<>();

    // FIXME: Create useful error
    try {
      createPlayers(playerMap);
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.version = version;
    this.playerMap = playerMap;
    this.stackable = stackable;

    createDeck();
    myDiscardPile.push(myDeck.popLastCard());
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
    myDiscardPile = new Stack<>();
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
      result.add(p.getHand().size());
    }
    return result;
  }

  @Override
  public void discardCard(Card c) {
    myDiscardPile.push(c);
  }


  @Override
  public String getLastCardThrownType() {
    return myDiscardPile.peek().getType();
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
    List<ViewCardInterface> cards = new ArrayList<>();
    for (ViewCardInterface card : myPlayers.get(currentPlayer).getHand()) {
     cards.add(card);
    }
    return cards;
  }

  @Override
  public void addDraw(int drawAmount) {
    impendingDraw += drawAmount;
  }

  @Override
  public Card getNextCard() {
    return myDeck.popLastCard();
  }

  @Override
  public List<Card> noPlayDraw() {
    return myDrawRule.noPlayDraw(this);
  }

  @Override
  public boolean canPlayCard(Card cardToPlay) {
    return myRules.stream().anyMatch(rule -> rule.canPlay(myDiscardPile.peek(), cardToPlay));
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
          String.format(gameStateResources.getString("PlayerClassBase"), gameStateResources.getString(playerMap.get(name))));
      Player player = (Player) playerClass.getDeclaredConstructor(String.class,
          GameStatePlayerInterface.class).newInstance(name, this);
      myPlayers.add(player);
    }
  }


  private void createDeck(){
    ResourceBundle deckProperties = ResourceBundle.getBundle(
            "ooga.model.gameState." + version + "Deck");

    List<String> colors = List.of(deckProperties.getString("Colors").split(","));
    List<String> actionCards = List.of(deckProperties.getString("ActionCards").split(","));
    int numActionCards = Integer.parseInt(deckProperties.getString("NumberOfAction"));

    List<String> numberCards = List.of(deckProperties.getString("NumberCards").split(","));
    int numNumberCards = Integer.parseInt(deckProperties.getString("NumberOfNumber"));

    List<String> wildCards = List.of(deckProperties.getString("WildCards").split(","));
    int numWildCards = Integer.parseInt(deckProperties.getString("NumberOfWild"));

    List<Card> cards = new ArrayList<Card>();

    createCardsFromData(colors, numActionCards, actionCards, cards);
    createCardsFromData(colors, numNumberCards, numberCards, cards);
    createCardsFromData(colors, numWildCards, wildCards, cards);

    Collections.shuffle(cards);

    myDeck.addAll(cards);

  }

  private void createCardsFromData(List<String> colorList,
                                         int numCards,
                                         List<String> cardTypeList,
                                         List<Card> deckList){

    for(String type : cardTypeList){
      for(int i = 0; i < numCards; i++){
        Card newCard;
        for(String color : colorList){

          try{
            int n = Integer.parseInt(type);
            newCard = myCardFactory.makeCard("Number", n, color);
          }
          catch(NumberFormatException e){
            newCard = myCardFactory.makeCard(type, -1, color);
          }
          deckList.add(newCard);
        }
      }
    }
  }

  private DrawRuleInterface createDrawRule()
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    Class<?> clazz = Class.forName(String.format(gameStateResources.getString("DrawRuleBase"), gameStateResources.getString("DrawRule")));
    return (DrawRuleInterface) clazz.getDeclaredConstructor().newInstance();
  }

  private List<RuleInterface> createRules()
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    String base = gameStateResources.getString("PlayRulesBase");
    List<RuleInterface> ret = new ArrayList<>();
    for (String rule : gameStateResources.getString("PlayRules").split(",")){
      Class<?> clazz = Class.forName(String.format(base, rule));
      ret.add((RuleInterface) clazz.getDeclaredConstructor().newInstance());
    }
    return ret;
  }

}