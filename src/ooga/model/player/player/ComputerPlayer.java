package ooga.model.player.player;

import ooga.model.cards.CardInterface;
import ooga.model.instanceCreation.ReflectionErrorException;
import ooga.model.player.playerGroup.PlayerGroupPlayerInterface;

/**
 * @author Paul Truitt
 * <p>
 * Subclass of player that is automated
 */
public class ComputerPlayer extends Player {

  public ComputerPlayer(String name, PlayerGroupPlayerInterface group) {
    super(name, group);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void playCard() throws ReflectionErrorException {
    int position = 0;
    PlayerGroupPlayerInterface group = super.getMyGroup();
    for (CardInterface card : super.getMyHand()) {
      if (group.canPlayCard(card)) {
        try {
          super.getMyHand().play(position, group, this);
        } catch (Exception e){
          e.printStackTrace();
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
    // FIXME: Figure out how to make better choice
    return "Red";
  }
}
