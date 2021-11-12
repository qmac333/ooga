package ooga.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
    ChoiceBox<String> choiceBox = new ChoiceBox<String>();
    choiceBox.setValue("Language");
    choiceBox.getItems().add("English");
    choiceBox.getItems().add("Spanish");
    choiceBox.setOnAction(e -> controller.languageHandler());

    Button loadExisting = new Button("Load Existing Game");
    loadExisting.setOnAction(e -> controller.loadExistingHandler());
    Button loadNew = new Button("Load New Game");
    loadNew.setOnAction(e -> controller.loadNewHandler());
    Button playButton = new Button("Play");
    playButton.setOnAction(e -> controller.playButtonHandler());
    root.getChildren().addAll(choiceBox, loadExisting, loadNew, playButton);
    Scene scene = new Scene(root, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
    return scene;
  }

  private void initDynamicView() {

  }

}
