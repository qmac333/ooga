package ooga.model.player;

import java.util.Optional;
import ooga.model.cards.Card;
import ooga.model.gameState.GameStatePlayerInterface;

/**
 * @author Paul Truitt
 *
 * Subclass of player that is automated
 */
public class ComputerPlayer extends Player {

  public ComputerPlayer(String name, GameStatePlayerInterface game) {
    super(name, game);
  }

  @Override
  public void playCard() {
    GameStatePlayerInterface game = super.getMyGame();
    Optional<Card> cardToPlay = super.getMyHand().stream().filter(game::canPlayCard).findAny();
    if (cardToPlay.isPresent()) {
      cardToPlay.get().executeAction(game);
    } else {
      super.addCard(game.getNextCard());
    }
  }
}
