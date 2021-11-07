package ooga.controller;

import java.util.function.Consumer;

public class UnoController {

    /**
     * initializes data structures and saves the input configuration filepath to a global variable
     * @param filepath of the configuration file used to define the Uno game parameters
     */
    public UnoController(String filepath){

    }

    /**
     * parses the configuration file and initializes the model
     */
    public void setupProgram(){

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

}
