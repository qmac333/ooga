package ooga.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
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

  SplashScreenController controller;

  public SplashScreen(SplashScreenController controller) {
    this.controller = controller;
  }

  public Scene setScene() {
    BorderPane borderPane = new BorderPane();
    borderPane.setTop(addGridPane());
    borderPane.setLeft(addVBox());
    borderPane.setBottom(addHBox());

    Scene scene = new Scene(borderPane, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
    return scene;
  }

  private GridPane addGridPane() {
    GridPane gridPane = new GridPane();
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    gridPane.setPadding(new Insets(15, 10, 15, 10));
    gridPane.setStyle("-fx-background-color: #336699;");

    Text checkForUpdates = new Text();
    checkForUpdates.setFont(Font.font("Arial Black", FontWeight.BLACK, 15));
    checkForUpdates.setFill(Color.WHITE);
    checkForUpdates.setTextAlignment(TextAlignment.CENTER);
    checkForUpdates.setText("Welcome to UNO".toUpperCase());
    gridPane.add(checkForUpdates, 20,0);

    ChoiceBox<String> choiceBox = new ChoiceBox<>();
    choiceBox.setValue("Language");
    choiceBox.getItems().add("English");
    choiceBox.getItems().add("Spanish");
    choiceBox.setOnAction(e -> controller.languageHandler());
    gridPane.add(choiceBox, 33, 0);
    
    return gridPane;
  }

  private VBox addVBox() {
    VBox root = new VBox();
    root.setAlignment(Pos.CENTER);
    root.setPadding(new Insets(15, 12, 15, 12));
    root.setSpacing(10);

    Button loadExisting = new Button("Load Existing Game");
    loadExisting.setPrefSize(130, 20);
    loadExisting.setOnAction(e -> controller.loadExistingHandler());
    Button loadNew = new Button("Load New Game");
    loadNew.setPrefSize(130, 20);
    loadNew.setOnAction(e -> {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setInitialDirectory(
              new File(Paths.get(".").toAbsolutePath().normalize() + "/data"));
      File selectedFile = fileChooser.showOpenDialog(null);
      controller.loadNewHandler(selectedFile.getAbsolutePath());
    });
    root.getChildren().addAll(loadExisting, loadNew);

    return root;
  }

  private HBox addHBox() {
    HBox root = new HBox();
    root.setAlignment(Pos.CENTER);
    root.setPadding(new Insets(15, 12, 15, 12));
    root.setSpacing(10);

    Button playButton = new Button("Play");
    playButton.setPrefSize(130, 20);
    playButton.setOnAction(e -> controller.playButtonHandler());

    root.getChildren().addAll(playButton);
    return root;
  }

  private void initDynamicView() {

  }

}
