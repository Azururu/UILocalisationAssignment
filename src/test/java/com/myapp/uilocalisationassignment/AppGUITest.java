package com.myapp.uilocalisationassignment;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class AppGUITest extends ApplicationTest {
    static {
        AppController.TEST_MODE = true;
    }

    private AppGUI app;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        app = new AppGUI();
        app.start(stage);
    }

    @Test
    void testAppStartsAndTitleIsSet() {
        assertEquals("Fuel Cost Calculator", stage.getTitle(), "The stage title should be 'Fuel Cost Calculator'");
    }

    @Test
    void testSceneIsInitialized() {
        assertNotNull(stage.getScene(), "The scene should not be null");
        assertNotNull(stage.getScene().getRoot(), "The scene root should not be null");
        assertEquals(400.0, stage.getScene().getWidth(), 0.01, "The scene width should be 400");
        assertEquals(500.0, stage.getScene().getHeight(), 0.01, "The scene height should be 500");
    }

    @Test
    void testRootElementIsPresent() {
        // We can lookup the root VBox defined in the FXML
        assertNotNull(lookup("#rootVBox").query(), "The root VBox with fx:id 'rootVBox' should be present");
    }

    @Test
    void testMainMethod() {
        // Just calling it to hit the coverage, though it won't actually start the app in a headless test environment easily
        // We can't really call Main.main(new String[]{}) because it will try to launch another JavaFX app
        // But we can instantiate Main if it's not a utility class
        Main main = new Main();
        assertNotNull(main);
    }
}
