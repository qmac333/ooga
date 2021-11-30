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

  public ComputerPlayer(String name, GameStatePlayerInterface game, Supplier<Integer> integerSupplier, Supplier<String> stringSupplier) {
    super(name, game, integerSupplier, stringSupplier);
  }

  @Override
  public void playCard() {
    int position = 0;
    GameStatePlayerInterface game = super.getMyGame();
    for (CardInterface card : super.getMyHand()) {
      if (game.canPlayCard(card)) {
        try {
          super.getMyHand().play(position, game, this);
        } catch (Exception e){
          e.printStackTrace();
        }
        return;
      }
      position++;
    }
    super.addCards(game.noPlayDraw());
  }

  @Override
  public String getColor(){
    // FIXME: Figure out how to make better choice
    return "Red";
  }
}
