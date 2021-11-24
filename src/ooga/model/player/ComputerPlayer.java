package ooga.model.player;

import java.util.Optional;
import java.util.function.Supplier;
import ooga.model.cards.Card;
import ooga.model.cards.CardInterface;
import ooga.model.gameState.GameStatePlayerInterface;

/**
 * @author Paul Truitt
 * <p>
 * Subclass of player that is automated
 */
public class ComputerPlayer extends Player {

  public ComputerPlayer(String name, GameStatePlayerInterface game, Supplier<Integer> supplier) {
    super(name, game, supplier);
  }

  @Override
  public void playCard() {
    int position = 0;
    GameStatePlayerInterface game = super.getMyGame();
    for (CardInterface card : super.getMyHand()) {
      if (game.canPlayCard(card)) {
        try {
          super.getMyHand().play(position, game);
        } catch (Exception e){
          e.printStackTrace();
        }
        return;
      }
      position++;
    }
    super.addCards(game.noPlayDraw());
  }
}
