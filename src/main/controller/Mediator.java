package main.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.model.GameData;

/**
 * Created by drew on 9/11/14.
 *
 * The Mediator
 *
 * The Mediator handles all actions and objects of the system.
 * It starts the game and sets the primary stage.
 */
public class Mediator extends Application {
    /**
     * The mediator object.
     */
    private static Mediator obj;

    /**
     * The Primary Stage.
     */
    private Stage primaryStage;

    /**
     * Constructs the singleton instance of the mediator.
     */
    public Mediator() {
        if (obj != null) {
            throw new RuntimeException("Already Initiated");
        }

    }

    /**
     * return the mediator object if it exists.
     * @return the mediator.
     */
    public static Mediator getObject() {
        if (obj == null) {
            obj = new Mediator();
        }
        return obj;
    }

    /**
     * launches Space Trader.
     * @param args the arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * JavaFX start method. Loads the first screen.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        loadScreen("Space Trader!", "../view/startScreen.fxml");
    }

    /**
     * Loads the screen passed into it. Used in all controller classes
     * to navigate around the game screens.
     * @param name the name of the value.
     * @param resource the resource.
     * @return true if screen was loaded, false otherwise.
     */
    public boolean loadScreen(String name, String resource) {
        try {
            FXMLLoader myLoader = new
                    FXMLLoader(getClass().getResource(resource));
            System.out.println(getClass().getResource(resource));
            Parent loadScreen = myLoader.load();
            ScreenController myScreenController =
                    myLoader.getController();
            myScreenController.loadMediator(this);
            primaryStage.setTitle(name);
            primaryStage.setScene(new Scene(loadScreen, GameData.WIDTH, GameData.HEIGHT));
            primaryStage.show();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}