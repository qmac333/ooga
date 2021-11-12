package ooga.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import ooga.controller.SplashScreenController;

public class SplashScreen {

  SplashScreenController controller;

  public SplashScreen(SplashScreenController controller) {
    this.controller = controller;
  }

  public Scene setScene() {
    VBox root = new VBox();
    root.setAlignment(Pos.CENTER);
    Button loadFile = new Button("Test");
    loadFile.setOnAction(e -> controller.playButtonHandler());
    root.getChildren().add(loadFile);
    Scene scene = new Scene(root);
    return scene;
  }

  private void initDynamicView() {

  }

}
