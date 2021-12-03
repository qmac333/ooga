package ooga.model.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import ooga.model.cards.CardInterface;
import ooga.model.cards.ViewCardInterface;
import ooga.model.gameState.GameStatePlayerInterface;
import ooga.model.hand.Hand;

public abstract class Player implements PlayerInterface {

  private Hand myHand;
  private String myName;
  private GameStatePlayerInterface myGame;
  private Supplier<Integer> myIntegerSupplier;
  private Supplier<String> myStringSupplier;

  public Player(String name, GameStatePlayerInterface game) {
    myName = name;
    myGame = game;
    myHand = new Hand();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract void playCard();

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
  public void flipHand(){
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
  public void flipGame(){
    myGame.flipCards();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void reverseGame(){
    myGame.reverseGamePlay();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void skipNextPlayer(){
    myGame.skipNextPlayer();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void skipEveryone(){
    myGame.skipEveryone();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void enforceDraw(int drawAmount){
    myGame.addDraw(drawAmount);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void discardColor(String color){
    Collection<CardInterface> cards = myHand.removeColor(color);
    for (CardInterface card : cards){
      myGame.discardCard(card);
    }
  }

  /**
   * Gives the player a hand
   *
   * @param hand Hand to give the player
   */
  public void loadHand(Hand hand){
    myHand = hand;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSuppliers(Supplier<Integer> integerSupplier, Supplier<String> stringSupplier){
    myIntegerSupplier = integerSupplier;
    myStringSupplier = stringSupplier;
  }

  /**
   * @return hand held by player
   */
  public Hand getMyHand() {
    return myHand;
  }

  // Returns the players Game
  protected GameStatePlayerInterface getMyGame() {
    return myGame;
  }

  // Returns supplier used to get Hand index
  protected Supplier<Integer> getMyIntegerSupplier(){
    return myIntegerSupplier;
  }

  // Returns supplier used to get Color
  protected Supplier<String> getMyStringSupplier(){
    return myStringSupplier;
  }
}
