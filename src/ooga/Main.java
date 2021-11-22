package ooga;

import javafx.application.Application;
import javafx.stage.Stage;
import ooga.controller.UnoController;
import ooga.util.Config;
import ooga.view.CardDisplay;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        CardDisplay.initializeCards();
        UnoController controller = new UnoController(stage);
        controller.start();
    }
}
