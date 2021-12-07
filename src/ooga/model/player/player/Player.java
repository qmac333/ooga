package ooga.model.player.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import ooga.model.cards.CardInterface;
import ooga.model.cards.ViewCardInterface;
import ooga.model.hand.Hand;
import ooga.model.instanceCreation.ReflectionErrorException;
import ooga.model.player.playerGroup.PlayerGroupPlayerInterface;

public abstract class Player implements PlayerCardInterface, ViewPlayerInterface,
    PlayerGameInterface {

  private Hand myHand;
  private String myName;
  private PlayerGroupPlayerInterface myGroup;
  private Supplier<Integer> myIntegerSupplier;
  private Supplier<String> myStringSupplier;
  private int myPoints;

  public Player(String name, PlayerGroupPlayerInterface group) {
    myName = name;
    myGroup = group;
    myHand = new Hand();
    myPoints = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract void playCard() throws ReflectionErrorException;

  /**
   * {@inheritDoc}
   */
  @Override
  public void addCards(Collection<CardInterface> cards) {
    myHand.add(cards);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return myName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getNumPoints() {
    int sum = 0;
    for (CardInterface c : myHand) {
      sum += c.getNum();
    }
    return sum;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHandSize() {
    return myHand.size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ViewCardInterface> getViewCards() {
    ArrayList<ViewCardInterface> ret = new ArrayList<>();
    for (CardInterface card : myHand) {
      ret.add((ViewCardInterface) card);
    }
    return ret;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void flipHand() {
    myHand.flip();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract String getColor();

  /**
   * {@inheritDoc}
   */
  @Override
  public void flipGame() {
    myGroup.flipGame();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void reverseGame() {
    myGroup.reverseOrder();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void skipNextPlayer() {
    myGroup.skipNextPlayer();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void skipEveryone() {
    myGroup.skipEveryone();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void enforceDraw(int drawAmount) {
    myGroup.enforceDraw(drawAmount);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void discardColor(String color) {
    Collection<CardInterface> cards = myHand.removeColor(color);
    for (CardInterface card : cards) {
      myGroup.discardCard(card);
    }
  }

  /**
   * Gives the player a hand
   *
   * @param hand Hand to give the player
   */
  @Override
  public void loadHand(Hand hand) {
    myHand = hand;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSuppliers(Supplier<Integer> integerSupplier, Supplier<String> stringSupplier) {
    myIntegerSupplier = integerSupplier;
    myStringSupplier = stringSupplier;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getPoints() {
    return myPoints;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void awardPoints(int amount) {
    myPoints += amount;
  }

  @Override
  public Collection<Integer> getValidIndexes() {
    List<Integer> indexes = new ArrayList<>();
    int currentIndex = 0;
    for (CardInterface card : myHand) {
      if (myGroup.canPlayCard(card)) {
        indexes.add(currentIndex);
      }
      currentIndex++;
    }
    return indexes;
  }

  /**
   * @return hand held by player
   */
  @Override
  public Hand getMyHand() {
    return myHand;
  }

  @Override
  public void dumpCards(){
    myHand = new Hand();
  }

  // Returns the players Game
  protected PlayerGroupPlayerInterface getMyGroup() {
    return myGroup;
  }

  // Returns supplier used to get Hand index
  protected Supplier<Integer> getMyIntegerSupplier() {
    return myIntegerSupplier;
  }

  // Returns supplier used to get Color
  protected Supplier<String> getMyStringSupplier() {
    return myStringSupplier;
  }
}
