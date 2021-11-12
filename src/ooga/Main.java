package ooga;

import javafx.application.Application;
import javafx.stage.Stage;
import ooga.controller.UnoController;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        UnoController controller = new UnoController(stage);
        controller.start();

    }
}

//Main:
//
//    -create a new instance of Controller(stage)
//    controller.start();
//
//
//    Controller (stage) implement SplashScreenController, UnoGameController:
//
//    loadFileHandler() {
//    ....
//    splashScreen.initDyanimicView();
//    }
//
//    start:
//    SplashScreen splashScreen = new SplashScreen(this);
//    stage.setScene(splashScreen.makeScene());
//    stage.show();
//
//    Splash Screen (SplashScreenController (interface)) {
//    makeScene() {
//
//    makes view objects
//    Play, loadFile button
//
//    }
//
//    initDynamicView() {
//    playButton.setAction(controller -> action);
//
//    }
//
//
//
//
//    }
//
//    Main Screen (UnoGameController controller) {
//    }
//
//    loadFileHandler();
