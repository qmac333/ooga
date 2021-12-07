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
