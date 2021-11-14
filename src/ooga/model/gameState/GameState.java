package ooga.model.gameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import ooga.model.Player;
import ooga.model.cards.Card;

public class GameState implements GameStateInterface, GameStateViewInterface {

  private int order;
  private int currentPlayer;
  private List<Player> players;
  private Stack<Card> discardPile;

  private int cardNumConstraint;
  private String cardColorConstraint;

  private int impendingDraw;

  private boolean skipNext;

  public GameState() {
    order = 1;
    skipNext = false;
    impendingDraw = 0;
    players = new ArrayList<>();
    discardPile = new Stack<>();
  }

  @Override
  public List<String> getPlayerNames() {
    return null;
  }

  @Override
  public List<Integer> getCardCounts() {
    return null;
  }

  @Override
  public void discardCard(Card c) {
    discardPile.push(c);
    cardColorConstraint = discardPile.peek().getColor();
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
    if (impendingDraw == 0) {
      // Basically tell the current player to play their turn
    } else {
      // Enforce Draw
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
  public Map<Integer, List<List<String>>> getCurrentPlayerCards() {
    return null;
  }

  @Override
  public void addDraw(int drawAmount) {
    impendingDraw += drawAmount;
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
}