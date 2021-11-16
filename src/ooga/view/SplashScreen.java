package ooga.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import ooga.controller.SplashScreenController;
import ooga.util.Config;

import java.io.File;
import java.nio.file.Paths;

public class SplashScreen implements GameScreen {

  private static final String TITLE = "WELCOME TO UNO";
  private static final String CSS_STYLE = "/ooga/resources/splashScreen.css";

  SplashScreenController controller;

  public SplashScreen(SplashScreenController controller) {
    this.controller = controller;
  }

  public Scene setScene() {
    BorderPane borderPane = new BorderPane();
    borderPane.setTop(addTopNode());
    borderPane.setLeft(addLeftNode());
    borderPane.setBottom(addBottomNode());

    Scene scene = new Scene(borderPane, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
    scene.getStylesheets()
            .add(SplashScreen.class.getResource(CSS_STYLE).toExternalForm());
    return scene;
  }

  private HBox addTopNode() {
    HBox hbox = new HBox();
    hbox.getStyleClass().add("hbox");

    Text checkForUpdates = new Text(TITLE);
    checkForUpdates.getStyleClass().add("text-title");
    hbox.getChildren().add(checkForUpdates);

    return hbox;
  }

  private VBox addLeftNode() {
    VBox root = new VBox();
    root.getStyleClass().add("vbox");

    Button loadExisting = new Button("Load Existing Game");
    loadExisting.setOnAction(e -> controller.loadExistingHandler());
    Button loadNew = new Button("Load New Game");
    loadNew.setOnAction(e -> chooseFile());

    ChoiceBox<String> choiceBox = new ChoiceBox<>();
    choiceBox.setValue("Language");
    choiceBox.getItems().add("English");
    choiceBox.getItems().add("Spanish");
    choiceBox.setOnAction(e -> controller.languageHandler());

    root.getChildren().addAll(loadExisting, loadNew, choiceBox);

    return root;
  }

  private void chooseFile() {
    FileChooser fileChooser = new FileChooser();
    //fileChooser.setInitialDirectory(new File(Paths.get(".").toAbsolutePath().normalize() + "/data"));
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
    fileChooser.getExtensionFilters().add(extFilter);
    File selectedFile = fileChooser.showOpenDialog(null);
    if(selectedFile != null){
      controller.loadNewHandler(selectedFile.getAbsolutePath());
    }
  }

  private HBox addBottomNode() {
    HBox root = new HBox();
    root.getStyleClass().add("hbox");

    Button playButton = new Button("Play");
    playButton.setOnAction(e -> controller.playButtonHandler());

    root.getChildren().addAll(playButton);
    return root;
  }

  private void initDynamicView() {

  }

}
