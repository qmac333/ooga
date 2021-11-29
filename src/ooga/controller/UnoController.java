package ooga.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Consumer;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ooga.model.gameState.GameState;
import ooga.model.gameState.GameStateViewInterface;
import ooga.view.GameScreen;
import ooga.view.LanguageScreen;
import ooga.view.SplashScreen;
import ooga.view.UnoDisplay;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.nio.file.Files;

public class UnoController implements LanguageScreenController, SplashScreenController, UnoDisplayController {

  private Stage stage;
  private LanguageScreen languageScreen;
  private SplashScreen splashScreen;
  private UnoDisplay unoDisplay;

  private Moshi moshi;
  private GameState model;

  private String currentVersion;
  private Map<String, String> currentPlayerMap;
  private int currentPointsToWin;
  private boolean currentStackable;

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
  // TODO: does the view do this directly through the GameStateViewInterface?
  public void setupConsumer(Consumer viewConsumer) {

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
   * Shows the splash screen of the application.
   */
  public void start() {
    if (languageScreen == null) {
      languageScreen = new LanguageScreen(this);
    }
    showScreen(languageScreen);
  }

  @Override
  public boolean setGameParameters(String version, Map<String, String> playerMap, String pointsToWin, boolean stackable){
    if(version != null && playerMap.size() > 0 && pointsToWin != null){
      try{
        currentPointsToWin = Integer.parseInt(pointsToWin);
        currentVersion = version;
        currentPlayerMap = playerMap;
        currentStackable = stackable;

        model = new GameState(currentVersion, currentPlayerMap, currentPointsToWin, currentStackable);
        return true;
      }
      catch(NumberFormatException e){
        sendAlert("Please Input a Numeric Value in the Points to Win Field");
      }
    }
    else{
      sendAlert("Please Input ALL Game Parameters (Version, Points to Win, Stackability and Players)");
    }
    return false;
  }

  /**
   * Creates new Uno game display
   */
  @Override
  public void playNewGame() {
    if(model != null){
      unoDisplay = new UnoDisplay(this);
      showScreen(unoDisplay);
    }
    else{
      sendAlert("Please Load a Configuration File or Manually Input Parameters");
    }
  }

  @Override
  public void loadExistingFile() {
    System.out.println("Loading a File");
  }

  /**
   * Retrieves model parameters from the specified JSON file using Moshi before initializing a new model (GameState) object
   * @param filepath of the chosen JSON
   */
  @Override
  public boolean loadNewFile(String filepath) {
    try{
      String json = getFileContent(filepath);
      JsonAdapter<GameState> jsonAdapter = moshi.adapter(GameState.class);
      model = jsonAdapter.fromJson(json);
      return true;
    }
    catch (IOException e) {
      //TODO: better error handling
      System.out.println(e.getMessage());
      sendAlert("Unrecognized File Format");
    }
    return false;
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
  public void saveCurrentFile() {

  }

  @Override
  public void setLanguage(String language) {
    if (splashScreen == null) {
      splashScreen = new SplashScreen(this, language);
    }
    showScreen(splashScreen);
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
