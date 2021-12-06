package ooga.view;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import ooga.model.cards.NumberCard;
import ooga.model.cards.ViewCardInterface;
import ooga.model.deck.CardPileViewInterface;
import ooga.model.gameState.GameStateViewInterface;
import ooga.model.player.player.ViewPlayerInterface;

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
  public List<ViewPlayerInterface> getPlayers() {
    return null;
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
  public CardPileViewInterface getDeck() {
    return null;
  }

  @Override
  public CardPileViewInterface getDiscardPile() {
    return null;
  }

  @Override
  public void playTurn() {

  }

  @Override
  public void setCalledUno(boolean uno) {

  }

  @Override
  public void createPlayers(Supplier<Integer> integerSupplier, Supplier<String> stringSupplier)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

  }

  @Override
  public void createPlayers(Supplier<Integer> supplier)
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

  }

  @Override
  public void setSuppliers(Supplier<Integer> integerSupplier, Supplier<String> stringSupplier) {

  }

  @Override
  public boolean userPicksCard() {
    return false;
  }

  @Override
  public Collection<Integer> getValidIndexes() {
    return null;
  }

  @Override
  public List<ViewCardInterface> getBlasterCards() {
    return null;
  }

  @Override
  public boolean blasterWentOff() {
    return false;
  }

  @Override
  public void createDeck(Map<String, Supplier<String>> map) {

  }

  @Override
  public boolean getEndGame() {
    return false;
  }

  public void addPlayer() {
    cardCounts.add(5);
    playerNames.add("Drew");
  }
}
