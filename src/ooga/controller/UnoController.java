package ooga.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import com.squareup.moshi.JsonDataException;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import ooga.controller.interfaces.LanguageScreenController;
import ooga.controller.interfaces.SplashScreenController;
import ooga.controller.interfaces.UnoDisplayController;
import ooga.controller.moshi.CardInterfaceAdapter;
import ooga.controller.moshi.GameStateJsonAdapter;
import ooga.model.gameState.GameState;
import ooga.model.gameState.GameStateViewInterface;
import ooga.view.CardDisplay;
import ooga.view.GameScreen;
import ooga.view.LanguageScreen;
import ooga.view.SplashScreen;
import ooga.view.maindisplay.BasicUnoDisplay;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.nio.file.Files;

public class UnoController implements LanguageScreenController, SplashScreenController, UnoDisplayController {
  private static final String SAVE_FILE_PATH = Paths.get(".", "\\data\\configuration_files\\Save Files").toAbsolutePath().normalize().toString();
  private static final String REQUIRED_MOD_FILEPATH = Paths.get(".", "\\data\\mods\\RequiredImages.txt").toAbsolutePath().normalize().toString();
  private static final String DISPLAY = "ooga.view.maindisplay.%sUnoDisplay";

  private Stage stage;
  private LanguageScreen languageScreen;
  private SplashScreen splashScreen;
  private BasicUnoDisplay unoDisplay;

  private Moshi moshi;
  private JsonAdapter<GameState> jsonAdapter;
  private GameState model;

  private String currentVersion;
  private String currentMod;
  private Map<String, String> currentPlayerMap;
  private int currentPoints;
  private boolean currentStackable;
  private String language = "English";
  private String colorThemeFilepath = "/ooga/resources/mainDisplay.css";

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
   * Sets the language for the game
   * @param language new language for the game
   */
  @Override
  public void setLanguage(String language) {
    this.language = language;
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
   * Sets the color theme for the game
   * @param cssFile the CSS filepath that UnoDisplay will use
   */
  @Override
  public void setColorThemeFilepath(String cssFile) {
    colorThemeFilepath = cssFile;
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
      currentPlayerMap = playerMap;
      currentPoints = pointsToWin;
      currentStackable = stackable;
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

      if (!validateMod()) {
        return false;
      };

      String version = String.format(DISPLAY, currentVersion);
      try {
        unoDisplay = (BasicUnoDisplay) Class.forName(version).getConstructor(UnoDisplayController.class, String.class, String.class).
                newInstance(this, language, colorThemeFilepath);
      } catch(Exception e) {
        e.printStackTrace();
      }

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
      currentPlayerMap = model.getPlayerMap();
      currentPoints = model.getPointsToWin();
      currentStackable = model.getStackable();
      return true;
    }
    catch (IOException | JsonDataException e){
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
   * Returns the user from the main Uno Game Screen to the initial Language Screen
   */
  @Override
  public void returnToSplashScreen() {
    unoDisplay = null;
    model = null;
    start();
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
      System.out.println(e.getMessage());
    }
    return false;
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
   * Gets the version of the UNO game that is currently being played
   * @return String representing the version
   */
  @Override
  public String getGameVersion() { return currentVersion; }

  /**
   * Gets the map of player names to player type
   * @return Map representing the players
   */
  @Override
  public Map<String, String> getPlayerMap(){
    return currentPlayerMap;
  }

  /**
   * Gets points required to win the game
   * @return int representing the points
   */
  @Override
  public int getPoints(){
    return currentPoints;
  }

  /**
   * Gets the parameter indicating whether +4 and +2 cards can be stacked
   * @return boolean representing stackability
   */
  @Override
  public boolean getStackable(){
    return currentStackable;
  }

  /**
   * @return boolean indicating whether a game in progress was loaded through a configuration file
   */
  @Override
  public boolean getLoadedGameInProgress(){
    return model.loadedGameInProgress;
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
  public BasicUnoDisplay getUnoDisplay(){
    return unoDisplay;
  }

  /**
   * Displays the given screen on the main stage
   * @param screen
   */
  private void showScreen(GameScreen screen) {
    stage.setScene(screen.setScene());
    stage.show();
  }

  /**
   * Validates that a selected mod has all required images.
   * @return true if the mod can be played, else return false
   */
  private boolean validateMod() {
    Scanner requiredImages;
    ResourceBundle modImages;
    try {
      requiredImages = new Scanner(new File(REQUIRED_MOD_FILEPATH));
      modImages = ResourceBundle.getBundle("ooga.resources.mods." + currentMod);
    }
    catch (Exception e) {
      return false;
    }

    while (requiredImages.hasNextLine()) {
      String line = requiredImages.nextLine();
      try {
        ImageIO.read(new FileInputStream(modImages.getString(line)));
      } catch (Exception e) {
        return false;
      }
    }

    // now, initialize all images
    CardDisplay.initializeCards(modImages);
    return true;

  }
}
