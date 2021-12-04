package ooga.model.player;

import java.util.function.Supplier;
import ooga.model.gameState.GameStatePlayerInterface;

public class HumanPlayer extends Player {

  public HumanPlayer(String name, PlayerGroupInterface group) {
    super(name, group);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void playCard() {
    int index = -1;
    index = super.getMyIntegerSupplier().get();
    try {
      if (index < 0){
        super.addCards(getMyGroup().noPlayDraw());
      } else {
        getMyHand().play(index, super.getMyGroup(), this);
      }
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getColor(){
    return super.getMyStringSupplier().get();
  }
}
