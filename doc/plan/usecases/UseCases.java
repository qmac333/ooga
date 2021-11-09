package ooga.apiexamples;

import ooga.controller.UnoController;
import ooga.model.GameStateInterface;
import ooga.model.PlayersInfo;
import ooga.view.HandListDisplay;
import ooga.view.TurnInfoDisplay;
import ooga.view.UnoDisplay;

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


  // UnoDisplay starts the game by creating a new controller, passing it the selected filepath, setting up the view-model
  // consumers, and continuously calling the controller's its step function
  private statis void ControllerUseCase(){
    UnoController controller = new UnoController("filepath");
    controller.setupProgram();
    HandListDisplay display = new HandListDisplay(controller);
    TurnInfoDisplay display = new TurnInfoDisplay(controller);

    boolean gameIsNotOver = true;
    while(gameIsNotOver){
      controller.step();
    }

  // A player draws a card, adding it to their hand
  private static void UseCase3() {
    Player player = new Player();
    Card c = new Card();

    player.addCard(c);
  }

  // A player plays a card, causing changes in the player's hand and/or the game itself
  private static void UseCase4() {
    GameState game = new GameState();
    Player player = new Player();
    Card card = new Card(game);

    game.addPlayer(player);
    player.addCard(card);
    player.playCard(0);
  }


  public static void main(String[] args) {
    UseCase1();
    UseCase2();
    ControllerUseCase();
    UseCase3();
    UseCase4();

  }

}
