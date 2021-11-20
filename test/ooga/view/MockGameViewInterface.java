package ooga.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ooga.model.cards.NumberCard;
import ooga.model.cards.ViewCardInterface;
import ooga.model.gameState.GameStateViewInterface;

public class MockGameViewInterface implements GameStateViewInterface {

  public static final int NUM_PLAYERS = 3;
  public static final int NUM_CARDS = 3;

  private List<Integer> cardCounts;
  private List<String> playerNames;

  public MockGameViewInterface() {
    cardCounts = new ArrayList<Integer>();
    playerNames = new ArrayList<String>();
    cardCounts.addAll(Arrays.asList(1, 2, 3));
    playerNames.addAll(Arrays.asList("Quentin", "Andrew", "Will"));
  }


  @Override
  public List<String> getPlayerNames() {
    return playerNames;
  }

  @Override
  public List<Integer> getCardCounts() {
    return cardCounts;
  }

  @Override
  public int getGameplayDirection() {
    return 1;
  }

  @Override
  public int getCurrentPlayer() {
    return 0;
  }

  @Override
  public List<ViewCardInterface> getCurrentPlayerCards() {
    List<ViewCardInterface> ret = new ArrayList<>();
    ret.add(new NumberCard("blue", 0));
    ret.add(new NumberCard("red", 1));
    ret.add(new NumberCard("yellow", 9));

    return ret;
  }

  @Override
  public ViewCardInterface getNextCard() {
    return null;
  }

  @Override
  public ViewCardInterface getLastCardThrown() {
    return null;
  }

  @Override
  public void playTurn() {

  }

  public void addPlayer() {
    cardCounts.add(5);
    playerNames.add("Drew");
  }
}
