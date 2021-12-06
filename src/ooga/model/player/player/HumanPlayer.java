package ooga.model.player.player;

import ooga.model.player.playerGroup.PlayerGroupPlayerInterface;

public class HumanPlayer extends Player {

  public HumanPlayer(String name, PlayerGroupPlayerInterface group) {
    super(name, group);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void playCard() {
    int index = super.getMyIntegerSupplier().get();
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
