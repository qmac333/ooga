package ooga.view;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import ooga.controller.interfaces.LanguageScreenController;
import ooga.util.Config;

import java.util.ResourceBundle;

public class LanguageScreen implements GameScreen {

  public static final String LANGUAGE_PICKER_CSS = "LanguagePicker";
  private static final String CSS_STYLE = "/ooga/resources/languageScreen.css";
  private static final String TITLE = "Please Choose A Language";

  private ResourceBundle languageResources;

  private LanguageScreenController controller;

  public LanguageScreen(LanguageScreenController controller) {
    this.controller = controller;
    languageResources = ResourceBundle.getBundle("ooga.resources.Languages");
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
    language.setId(LANGUAGE_PICKER_CSS);
    language.setValue("Language");
    for (String type : languageResources.keySet()) {
      language.getItems().add(languageResources.getString(type));
    }
    language.setOnAction(e -> {
      controller.setLanguage(language.getValue());
      controller.createSplashScreen(language.getValue());
    });

    hbox.getChildren().addAll(checkForUpdates, language);

    return hbox;
  }
}
