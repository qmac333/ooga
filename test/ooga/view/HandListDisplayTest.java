package ooga.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class HandListDisplayTest extends DukeApplicationTest {

    private HandListDisplay display;
    private MockController controller;

    @Override
    public void start(Stage stage) {
        CardDisplay.initializeCards();
        controller = new MockController();
        display = new HandListDisplay(controller, null, "English");
        Group root = new Group();
        root.getChildren().add(display.getDisplayableItem());
        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
    }

    @Test
    public void checkCards() {
        String[] expected = {"0", "1", "9"};
        pause(1000);

        for (int i = 0; i < MockGameViewInterface.NUM_CARDS; i++) {
            display.getDisplayableItem();
        }
    }

    private void pause(double millis) {
        long init = System.currentTimeMillis();
        while (System.currentTimeMillis() < init + millis) {
            // spin
        }
    }

}
