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


}
