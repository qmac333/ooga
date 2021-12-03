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

  @Override
  public abstract void playCard();

  @Override
  public void addCards(Collection<CardInterface> cards) {
    myHand.add(cards);
  }

  @Override
  public String getName() {
    return myName;
  }

  @Override
  public int getNumPoints() {
    int sum = 0;
    for (CardInterface c : myHand) {
      sum += c.getNum();
    }
    return sum;
  }

  @Override
  public int getHandSize() {
    return myHand.size();
  }

  @Override
  public List<ViewCardInterface> getViewCards() {
    ArrayList<ViewCardInterface> ret = new ArrayList<>();
    for (CardInterface card : myHand) {
      ret.add((ViewCardInterface) card);
    }
    return ret;
  }

  @Override
  public void flipHand(){
    myHand.flip();
  }

  @Override
  public abstract String getColor();

  @Override
  public void flipGame(){
    myGame.flipCards();
  }

  @Override
  public void reverseGame(){
    myGame.reverseGamePlay();
  }

  @Override
  public void skipNextPlayer(){
    myGame.skipNextPlayer();
  }

  @Override
  public void skipEveryone(){
    myGame.skipEveryone();
  }

  @Override
  public void enforceDraw(int drawAmount){
    myGame.addDraw(drawAmount);
  }

  @Override
  public void discardColor(String color){
    Collection<CardInterface> cards = myHand.removeColor(color);
    for (CardInterface card : cards){
      myGame.discardCard(card);
    }
  }

  public void loadHand(Hand hand){
    myHand = hand;
  }

  @Override
  public void setSuppliers(Supplier<Integer> integerSupplier, Supplier<String> stringSupplier){
    myIntegerSupplier = integerSupplier;
    myStringSupplier = stringSupplier;
  }

  public Hand getMyHand() {
    return myHand;
  }

  protected GameStatePlayerInterface getMyGame() {
    return myGame;
  }

  protected Supplier<Integer> getMyIntegerSupplier(){
    return myIntegerSupplier;
  }

  protected Supplier<String> getMyStringSupplier(){
    return myStringSupplier;
  }
}
