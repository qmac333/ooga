package ooga.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import ooga.controller.SplashScreenController;
import ooga.util.Config;

public class SplashScreen implements GameScreen {

  SplashScreenController controller;

  public SplashScreen(SplashScreenController controller) {
    this.controller = controller;
  }

  public Scene setScene() {
    VBox root = new VBox();
    root.setAlignment(Pos.CENTER);
    Button loadFile = new Button("Play");
    loadFile.setOnAction(e -> controller.playButtonHandler());
    root.getChildren().add(loadFile);
    Scene scene = new Scene(root, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
    return scene;
  }

  private void initDynamicView() {

  }

}
