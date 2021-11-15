package ooga.controller;

import java.io.IOException;
import java.util.function.Consumer;
import javafx.stage.Stage;
import ooga.model.gameState.GameStateViewInterface;
import ooga.view.GameScreen;
import ooga.view.SplashScreen;
import ooga.view.UnoDisplay;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.ToJson;

public class UnoController implements SplashScreenController, UnoDisplayController {

  private Stage stage;
  private SplashScreen splashScreen;
  private UnoDisplay unoScreen;

  private String filepath;
  private Moshi moshi;

  /**
   * initializes data structures for the UnoController
   *
   * @param stage the initial window for the application
   */
  public UnoController(Stage stage) {
    this.stage = stage;
    moshi = new Moshi.Builder().build();
    // TODO: add custom JSON adapter
  }

  /**
   * passes the view's consumer to the model so the model can call .accept() whenever it needs to
   * notify the view of a change in its state
   */
  public void setupConsumer(Consumer viewConsumer) {

  }

  /**
   * steps through one turn of the game by calling the corresponding model method MAYBE pause the
   * timeline if it is the user's turn? In this case you would unpause the timeline once the
   * playUserCard method got called...
   */
  public void step() {

  }

  /**
   * passes the user's selected card to play to the model
   *
   * @param index of the user's selected card from their hand
   */
  public void playUserCard(int index) {

  }

  /**
   * saves the current simulation/configuration to a JSON file
   */
  public void saveFile() {

  }

  /**
   * Shows the splash screen of the application.
   */
  public void start() {
    if (splashScreen == null) {
      splashScreen = new SplashScreen(this);
    }
    showScreen(splashScreen);
  }

  @Override
  public void playButtonHandler() {
    if (unoScreen == null) {
      unoScreen = new UnoDisplay(this);
    }
    showScreen(unoScreen);
  }

  @Override
  public void loadExistingHandler() {
    System.out.println("Loading a File");
  }

  @Override
  public void loadNewHandler(String filepath) {
    String json = getFileContent(filepath);
    System.out.println("Loading a File");
    JsonAdapter<GameStateJSON> jsonAdapter = moshi.adapter(GameStateJSON.class);
    try{
      GameStateJSON myGameStateJSON = jsonAdapter.fromJson(json);
    }
    catch (IOException e) {
      //TODO: better error handling
      System.out.println(e.getMessage());
    }
  }

  // TODO: get JSON file content from filepath
  private String getFileContent(String filepath){
    String json = "";
    return json;
  }

  @Override
  public void languageHandler() {
      System.out.println("Chose a language");
  }


  @Override
  public void backButtonHandler() {
    start();
  }

  @Override
  public GameStateViewInterface getGameState() {
    return null;
  }

  private void showScreen(GameScreen screen) {
    stage.setScene(screen.setScene());
    stage.show();
  }
}
