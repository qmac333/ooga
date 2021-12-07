package ooga.model.player.player;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import ooga.model.cards.CardInterface;
import ooga.model.instanceCreation.ReflectionErrorException;
import ooga.model.player.playerGroup.PlayerGroupPlayerInterface;
import ooga.util.Log;

/**
 * @author Paul Truitt
 * <p>
 * Subclass of player that is automated
 */
public class ComputerPlayer extends Player {

  private static final String LOG_FILE = ".\\data\\logMessages.txt";

  public ComputerPlayer(String name, PlayerGroupPlayerInterface group) {
    super(name, group);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void playCard() throws ReflectionErrorException, IOException {
    int position = 0;
    PlayerGroupPlayerInterface group = super.getMyGroup();
    for (CardInterface card : super.getMyHand()) {
      if (group.canPlayCard(card)) {
        try {
          super.getMyHand().play(position, group, this);
        } catch (Exception e){
          Log log = new Log(LOG_FILE, MethodHandles.lookup().lookupClass().toString());
          log.getLogger().setLevel(Level.WARNING);
//          log.getLogger().warning(logMsg);
        }
        return;
      }
      position++;
    }
    super.addCards(group.noPlayDraw());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getColor(){
    return getMyHand().getMaxColor();
  }
}
