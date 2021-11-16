package ooga.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ooga.model.gameState.GameState;
import ooga.model.gameState.GameStateViewInterface;
import ooga.view.GameScreen;
import ooga.view.SplashScreen;
import ooga.view.UnoDisplay;
import ooga.model.gameState.GameState;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.nio.file.Files;

public class UnoController implements SplashScreenController, UnoDisplayController {

  private Stage stage;
  private SplashScreen splashScreen;
  private UnoDisplay unoDisplay;

  private Moshi moshi;
  private GameState model;

  /**
   * initializes data structures for the UnoController
   *
   * @param stage the initial window for the application
   */
  public UnoController(Stage stage) {
    this.stage = stage;
    moshi = new Moshi.Builder().add(new GameStateJsonAdapter()).build();
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
    System.out.println("Played card "+index);
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

  /**
   * Creates new Uno game display
   */
  @Override
  public void playButtonHandler() {
    if(model != null){
      // TODO: pass the model object to the view
      unoDisplay = new UnoDisplay(this);
      showScreen(unoDisplay);
    }
    else{
      sendAlert("Please Load a Configuration File");
    }
  }

  @Override
  public void loadExistingHandler() {
    System.out.println("Loading a File");
  }

  /**
   * Retrieves model parameters from the specified JSON file using Moshi before initializing a new model (GameState) object
   * @param filepath of the chosen JSON
   */
  @Override
  public void loadNewHandler(String filepath) {
    try{
      String json = getFileContent(filepath);
      JsonAdapter<GameState> jsonAdapter = moshi.adapter(GameState.class);
      model = jsonAdapter.fromJson(json);
    }
    catch (IOException e) {
      //TODO: better error handling
      System.out.println(e.getMessage());
    }
  }

  /**
   * Retrieves the content in the JSON file specified by the input
   * @param filepath of the JSON file
   * @return content of the JSON file specified by the filepath
   * @throws IOException
   */
  public String getFileContent(String filepath) throws IOException{
    Path path = Paths.get(filepath);
    String jsonContent = Files.readString(path);
    return jsonContent;
  }

  /**
   * Saves the current simulation/configuration to a JSON file
   */
  @Override
  public void saveCurrentHandler() {

  }

  @Override
  public void languageHandler() {
      System.out.println("Choose a language");
  }


  @Override
  public void backButtonHandler() {
    start();
  }

  @Override
  public GameStateViewInterface getGameState() {
    return model;
  }

  public GameState getModel(){
    return model;
  }

  private void showScreen(GameScreen screen) {
    stage.setScene(screen.setScene());
    stage.show();
  }

  // displays alert/error message to the user
  private void sendAlert(String alertMessage) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setContentText(alertMessage);
    alert.show();
  }
}
