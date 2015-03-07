package main.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.model.GameData;
import main.model.Player;
import main.model.Ship;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Benjamin_Wilson on 10/20/14.
 */
public class ShipyardController implements ScreenController, Initializable {

    //CHECKSTYLE:OFF
    @FXML private ToggleGroup selectedShip;
    @FXML private Text currentMoney;

    private String currMon = "Current Money:";

    private Player player = GameData.getObject().getPlayer();
    //CHECKSTYLE:ON

    /**
     * The mediator object.
     */
    private Mediator mediator;
    /**
     * JavaFX default function for dynamically initializing values on screen.
     * Sets the text for the player's current money.
     * @param location
     * @param resourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        currentMoney.setText(currMon + Integer.toString(player.getMoney()));
    }

    @Override
    /**
     * Loads the mediator.
     * @param mediator the mediator.
     */
    public void loadMediator(Mediator mediator) { this.mediator = mediator; }

    /**
     * Purchases a ship.
     */
    public void buyShip() {
        Ship ship = Ship.SHIPS.get(((RadioButton) selectedShip.getSelectedToggle()).getText());
        if (player.getMoney() >= ship.getCost()) {
            player.setShip(ship);
            player.changeMoney(-ship.getCost());
            currentMoney.setText(currMon + Integer.toString(player.getMoney()));
            Stage dialog = new Stage();
            dialog.setTitle("Success!");
            dialog.initStyle(StageStyle.UTILITY);
            Text errorText = new Text(25, 25, "Successfully purchased " + ((RadioButton) selectedShip.getSelectedToggle()).getText());
            VBox dialogHBox = new VBox();
            dialogHBox.getChildren().addAll(errorText);
            dialogHBox.setAlignment(Pos.CENTER);
            dialogHBox.setSpacing(20);
            Scene scene = new Scene(dialogHBox, 400, 150);
            dialog.setHeight(150);
            dialog.setWidth(400);
            dialog.setScene(scene);
            dialog.show();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            mediator.loadScreen("Space Trader!", "../view/CurrentSystem.fxml");
        } else {
            Stage dialog = new Stage();
            dialog.setTitle("Error!");
            dialog.initStyle(StageStyle.UTILITY);
            Button cancelButton = new Button("OK");
            cancelButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dialog.close();
                }
            });
            Text errorText = new Text(25, 25, "You don't have enough money for that ship!");
            VBox dialogHBox = new VBox();
            dialogHBox.getChildren().addAll(errorText, cancelButton);
            dialogHBox.setAlignment(Pos.CENTER);
            dialogHBox.setSpacing(20);
            Scene scene = new Scene(dialogHBox, 400, 150);
            dialog.setHeight(150);
            dialog.setWidth(400);
            dialog.setScene(scene);
            dialog.show();
        }
    }

    public void buyGadgets()
    {
        mediator.loadScreen("GADGETS!", "../view/Gadget.fxml");
    }

    public void back()
    {
        mediator.loadScreen("Space Trader!", "../view/CurrentSystem.fxml");
    }
}
