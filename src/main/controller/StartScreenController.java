package main.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;

/**
 * Start Screen Controller
 *
 * Lets the player start the game or quit the game. Serves as the mai menu
 * of the game.
 */
public class StartScreenController implements ScreenController {

    /**
     * The mediator object.
     */
    private Mediator mediator;

    /**
     * Gets the instance of the Mediator.
     * @param mediator the mediator object.
     */
    public void loadMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    /**
     * Handles the action event for the start game button.
     * Sends the player to the manage game screen
     * @param actionEvent the button click being called.
     * @throws Exception an exception.
     */
    public void startGame(ActionEvent actionEvent) throws Exception {
        mediator.loadScreen("Space Trader!", "../view/manageGameScreen.fxml");
    }

    /**
     * Handles the action event for the quit button.
     * Exits the game
     * @param actionEvent the button click being called.
     */
    public void handleQuit(ActionEvent actionEvent) {
        Platform.exit();
    }
}