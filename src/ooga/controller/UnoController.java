package ooga.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import com.squareup.moshi.JsonDataException;
import javafx.stage.Stage;
import ooga.model.gameState.GameState;
import ooga.model.gameState.GameStateViewInterface;
import ooga.view.GameScreen;
import ooga.view.LanguageScreen;
import ooga.view.SplashScreen;
import ooga.view.maindisplay.UnoDisplay;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.nio.file.Files;

public class UnoController implements LanguageScreenController, SplashScreenController, UnoDisplayController {
  private static final String SAVE_FILE_PATH = Paths.get(".", "\\data\\configuration_files\\Save Files").toAbsolutePath().normalize().toString();

  private Stage stage;
  private LanguageScreen languageScreen;
  private SplashScreen splashScreen;
  private UnoDisplay unoDisplay;

  private Moshi moshi;
  private JsonAdapter<GameState> jsonAdapter;
  private GameState model;

  private String currentVersion;
  private String currentMod;
  private String language = "English";
  private String colorTheme;

  /**
   * initializes data structures for the UnoController
   *
   * @param stage the initial window for the application
   */
  public UnoController(Stage stage) {
    this.stage = stage;
    moshi = new Moshi.Builder().add(new CardInterfaceAdapter()).add(new GameStateJsonAdapter()).build();
    jsonAdapter = moshi.adapter(GameState.class);
  }

  /**
   * Shows the splash screen of the application.
   */
  public void start() {
    languageScreen = new LanguageScreen(this);
    showScreen(languageScreen);
  }

  /**
   * Creates new model (GameState) object based on the input parameters provided
   * @param version **see GameState documentation**
   * @param playerMap **see GameState documentation**
   * @param pointsToWin **see GameState documentation**
   * @param stackable **see GameState documentation**
   * @return boolean indicating the successful creation of a new model
   */
  @Override
  public boolean setGameParameters(String version, Map<String, String> playerMap, int pointsToWin, boolean stackable){
    if(version != null && playerMap.size() > 0 && pointsToWin > 0){
      currentVersion = version;
      model = new GameState(version, playerMap, pointsToWin, stackable);
      return true;
    }
    return false;
  }

  /**
   * Creates a new game display and shows it to the user
   * @return boolean indicating successful creation of a new game
   * @param mod
   */
  @Override
  public boolean playNewGame(String mod) {
    if(model != null){
      currentMod = mod;
      unoDisplay = new UnoDisplay(this, language, colorTheme);
      showScreen(unoDisplay);
      splashScreen = null;
      return true;
    }
    return false;
  }

  /**
   * Retrieves model parameters from the specified JSON file using Moshi before creating a new model (GameState) object
   * @param filepath of the chosen JSON
   * @return boolean indicating successful parsing of the provided file and creation of a new model
   */
  @Override
  public boolean loadFile(String filepath) {
    try{
      String json = getFileContent(filepath);
      model = jsonAdapter.fromJson(json);
      currentVersion = model.getVersion();
      return true;
    }
    catch (IOException | JsonDataException e){
      // TODO: this
      System.out.println(e.getMessage());
    }
    return false;
  }

  /**
   * Retrieves the content in the JSON file at the given path
   * @param filepath of the JSON file
   * @return content of the JSON file
   * @throws IOException
   */
  private String getFileContent(String filepath) throws IOException{
    Path path = Paths.get(filepath);
    String jsonContent = Files.readString(path);
    return jsonContent;
  }

  /**
   * Saves the current simulation/configuration to a JSON file
   * @param filename desired name of file (NOT filepath)
   * @return boolean indicating successful saving of the game's state to a JSON file with the given filename
   */
  @Override
  public boolean saveCurrentFile(String filename) {
    try{
      if(model != null){
        Path path = Paths.get(SAVE_FILE_PATH + "\\" + filename + ".json");
        FileWriter writer = new FileWriter(path.toFile());
        String json = jsonAdapter.toJson(model);
        writer.write(json);
        writer.flush();
        writer.close();
        return true;
      }
    }
    catch (IOException e){
      // TODO: this
      System.out.println(e.getMessage());
    }
    return false;
  }

  /**
   * Creates a new splash screen in the given language
   * @param language desired language
   */
  @Override
  public void createSplashScreen(String language) {
    if (splashScreen == null) {
      splashScreen = new SplashScreen(this, language);
    }
    showScreen(splashScreen);
    languageScreen = null;
  }

  /**
   * Returns the user from the main Uno Game Screen to the initial Language Screen
   */
  // TODO: Rename function to a verb - standard convention
  @Override
  public void toSplashScreen() {
    unoDisplay = null;
    model = null;
    start();
  }

  /**
   * Provides the GameState interface to the view, giving it access to only the methods it needs
   * @return the GameStateViewInterface object
   */
  @Override
  public GameStateViewInterface getGameState() {
    return model;
  }

  /**
   * Gets the version of the UNO game that is currently being played.
   * @return String representing the version
   */
  @Override
  public String getGameVersion() { return currentVersion; }

  /**
   * Gets the Mod of the UNO game that is currently being played.
   * @return String representing the mod
   */
  @Override
  public String getMod(){
    return currentMod;
  }

  /**
   * @return the SplashScreen object - FOR TESTING PURPOSES ONLY
   */
  public LanguageScreen getLanguageScreen(){
    return languageScreen;
  }

  /**
   * @return the SplashScreen object - FOR TESTING PURPOSES ONLY
   */
  public SplashScreen getSplashScreen(){
    return splashScreen;
  }

  /**
   * @return the GameState object - FOR TESTING PURPOSES ONLY
   */
  public GameState getModel(){
    return model;
  }

  /**
   * @return the UnoDisplay object - FOR TESTING PURPOSES ONLY
   */
  public UnoDisplay getUnoDisplay(){
    return unoDisplay;
  }


  /**
   * Sets the language for the game
   * @param language new language for the game
   */
  @Override
  public void setLanguage(String language) {
    this.language = language;
  }


  /**
   * Sets the color theme for the game
   * @param cssFile the css file the game will use
   */
  @Override
  public void setColorTheme(String cssFile) {
    colorTheme = cssFile;
  }

  /**
   * Displays the given screen on the main stage
   * @param screen
   */
  private void showScreen(GameScreen screen) {
    stage.setScene(screen.setScene());
    stage.show();
  }
}
