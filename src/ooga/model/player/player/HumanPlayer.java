package ooga.model.player.player;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import ooga.model.player.playerGroup.PlayerGroupPlayerInterface;
import ooga.util.Log;

public class HumanPlayer extends Player {

  private static final String LOG_FILE = ".\\data\\logMessages.txt";

  public HumanPlayer(String name, PlayerGroupPlayerInterface group) {
    super(name, group);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void playCard() throws IOException {
    int index = super.getMyIntegerSupplier().get();
    try {
      if (index < 0){
        super.addCards(getMyGroup().noPlayDraw());
      } else {
        getMyHand().play(index, super.getMyGroup(), this);
      }
    } catch (Exception e){
      Log log = new Log(LOG_FILE, MethodHandles.lookup().lookupClass().toString());
      log.getLogger().setLevel(Level.WARNING);
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
