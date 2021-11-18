package ooga.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
  public List<List<String>> getCurrentPlayerCards() {
    List<List<String>> ret = new ArrayList<>();
    List<String> firstCard = new ArrayList<>();
    firstCard.add("0");
    firstCard.add("blue");

    List<String> secondCard = new ArrayList<>();
    secondCard.add("1");
    secondCard.add("red");

    List<String> thirdCard = new ArrayList<>();
    thirdCard.add("9");
    thirdCard.add("yellow");

    ret.add(firstCard);
    ret.add(secondCard);
    ret.add(thirdCard);
    return ret;
  }

  @Override
  public void playTurn() {

  }

  public void addPlayer() {
    cardCounts.add(5);
    playerNames.add("Drew");
  }
}
