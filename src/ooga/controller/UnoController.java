package ooga.controller;

import java.util.function.Consumer;
import javafx.stage.Stage;
import ooga.view.GameScreen;
import ooga.view.SplashScreen;
import ooga.view.UnoDisplay;

public class UnoController implements SplashScreenController, UnoDisplayController {

    private Stage stage;

    /**
     * initializes data structures for the UnoController
     * @param stage the initial window for the application
     */
    public UnoController(Stage stage){
        this.stage = stage;
    }

    /**
     * parses the configuration file and initializes the model
     * @param filepath of the configuration file used to define the Uno game parameters
     */
    public void setupProgram(String filepath) {

    }

    /**
     * passes the view's consumer to the model so the model can call .accept() whenever it needs to notify the view of
     * a change in its state
     */
    public void setupConsumer(Consumer viewConsumer){

    }

    /**
     * steps through one turn of the game by calling the corresponding model method
     * MAYBE pause the timeline if it is the user's turn? In this case you would unpause the timeline once the
     * playUserCard method got called...
     */
    public void step(){

    }

    /**
     * passes the user's selected card to play to the model
     * @param index of the user's selected card from their hand
     */
    public void playUserCard(int index){

    }

    /**
     * saves the current simulation/configuration to a JSON file
     */
    public void saveFile(){

    }

    /**
     * Starts up the application, creates a splash screen.
     */
    public void start() {
        showScreen(new SplashScreen(this));
    }

    @Override
    public void playButtonHandler() {
        showScreen(new UnoDisplay(this));
    }

    @Override
    public void backButtonHandler() {
        start();
    }

    @Override
    public void loadExistingHandler() {
        System.out.println("Loaded a File");
    }

    @Override
    public void loadNewHandler() {
        System.out.println("Loading a new game");
    }

    @Override
    public void languageHandler() {
        System.out.println("Chose a language");
    }

    private void showScreen(GameScreen screen) {
        stage.setScene(screen.setScene());
        stage.show();
    }
}
