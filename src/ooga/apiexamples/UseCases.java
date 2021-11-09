package ooga.apiexamples;

import ooga.controller.UnoController;
import ooga.model.GameStateInterface;
import ooga.model.PlayersInfo;
import ooga.view.HandListDisplay;
import ooga.view.TurnInfoDisplay;

public class UseCases {

  // A player plays a reverse card, altering the direction of gameplay.
  private static void UseCase1() {
    // create a turn info display object
    TurnInfoDisplay display = new TurnInfoDisplay(new UnoController("foo"));
    // create mock data to pass to the front end
    PlayersInfo newPlayers = new DummyPlayerInfo();

    display.accept(newPlayers); // data is passed from back end to the front end
  }

  // A player draws a card, causing their hand to update both in the model and the view.
  private static void UseCase2() {
    HandListDisplay display = new HandListDisplay(new UnoController("foo"));
    GameStateInterface state = new DummyGameState();

    // new hand is passed from the back end to the front end
    display.accept(state.getCurrentPlayerCards());

  }


  public static void main(String[] args) {
    UseCase1();
    UseCase2();


  }

}