package ooga.apiexamples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ooga.model.cards.OneSidedCard;
import ooga.model.cards.CardInterface;
import ooga.model.gameState.GameStateInterface;
import ooga.model.player.player.Player;

/**
 * Class for mocking out the game state.
 */
public class DummyGameState implements GameStateInterface {

  Map<Integer, List<List<String>>> hand;

  public DummyGameState() {
    hand = new HashMap<>();
    hand.put(0, new ArrayList<>());
    hand.put(-1, new ArrayList<>());
    hand.get(0).add(new ArrayList<>(Arrays.asList("Red", "0")));
    hand.get(0).add(new ArrayList<>(Arrays.asList("Yellow", "0")));
    hand.get(-1).add(new ArrayList<>(Arrays.asList("Blue", "Reverse")));


  }

  @Override
  public void playTurn() {

  }

  @Override
  public void discardCard(CardInterface c) {

  }

  @Override
  public String getLastCardThrownType() {
    return null;
  }

  @Override
  public void reverseGamePlay() {

  }

  @Override
  public void skipNextPlayer() {

  }

  @Override
  public void addPlayer(Player p) {

  }

  @Override
  public int getCurrentPlayer() {
    return 0;
  }

  @Override
  public void addDraw(int drawAmount) {

  }

  @Override
  public OneSidedCard getNextCard() {
    return null;
  }

  @Override
  public int getOrder() {
    return 0;
  }
}
