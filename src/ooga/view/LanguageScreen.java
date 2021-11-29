package ooga.view;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import ooga.controller.LanguageScreenController;
import ooga.util.Config;

public class LanguageScreen implements GameScreen {

  private static final String CSS_STYLE = "/ooga/resources/languageScreen.css";
  private static final String TITLE = "Please Choose A Language";

  private LanguageScreenController controller;

  public LanguageScreen(LanguageScreenController controller) {
    this.controller = controller;
  }

  public Scene setScene() {
    BorderPane borderPane = new BorderPane();
    borderPane.setTop(addTopNode());

    Scene scene = new Scene(borderPane, Config.LANGUAGE_SCREEN_WIDTH,
        Config.LANGUAGE_SCREEN_HEIGHT);
    scene.getStylesheets()
        .add(LanguageScreen.class.getResource(CSS_STYLE).toExternalForm());
    return scene;
  }

  private Node addTopNode() {
    HBox hbox = new HBox();
    hbox.getStyleClass().add("hbox");

    Text checkForUpdates = new Text(TITLE);
    checkForUpdates.getStyleClass().add("text-title");

    ChoiceBox<String> language = new ChoiceBox<>();
    language.setValue("Language");
    language.getItems().add("English");
    language.getItems().add("Spanish");
    language.setOnAction(e -> controller.setLanguage(language.getValue()));

    hbox.getChildren().addAll(checkForUpdates, language);

    return hbox;
  }
}
