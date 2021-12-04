package ooga.model.player;

import ooga.model.cards.CardInterface;

/**
 * @author Paul Truitt
 * <p>
 * Subclass of player that is automated
 */
public class ComputerPlayer extends Player {

  public ComputerPlayer(String name, PlayerGroupInterface group) {
    super(name, group);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void playCard() {
    int position = 0;
    PlayerGroupInterface group = super.getMyGroup();
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
