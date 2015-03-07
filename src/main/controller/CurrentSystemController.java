package main.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.model.GameData;
import main.model.Player;
import main.model.SolarSystem;
import main.model.Universe;

import java.awt.Point;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Lance Hasson on 9/25/2014.
 *
 * Current System Controller Class
 * This class manages loading the graphical view of each system
 * as the player visits them. It takes data stored in the player
 * object and uses that to dynamically load each system based on
 * what resources, tech levels, location, etc. it has.
 */
public class CurrentSystemController implements ScreenController, Initializable {

    /**
     * The current system name.
     */
    @FXML private Text currentSystemName;
    /**
     * The current system location.
     */
    @FXML private Text currentSystemLoc;

    /**
     * Mediator Refrence.
     */
    private Mediator mediator;
    /**
     * Player Refrence.
     */
    private Player player;
    /**
     * Current System refrence.
     */
    private SolarSystem currentSystem;
    /**
     * Name of Current System.
     */
    private String sysName;
    /**
     * The Current System Loc.
     */
    private Point sysLocation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        player = GameData.getObject().getPlayer();
        currentSystem = player.getCurrentSystem();
        sysLocation = currentSystem.getLocation();
        sysName = currentSystem.getName();

        currentSystemName.setText("Your Current System: " + sysName);
        currentSystemLoc.setText("Location: " + sysLocation.toString());
    }

    /**
     * Gets the instance of the mediator.
     * @param mediator singleton mediator object.
     */
    public void loadMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    /**
     * Handles the goToMarketplace fxml button action event,
     * which sends the player to the marketplace screen.
     * @param actionEvent the action that occurs.
     */
    public void goToMarketplace(ActionEvent actionEvent) {
        mediator.loadScreen("The Marketplace", "../view/Marketplace.fxml");
    }

    /**
     * This goes to travel.
     * @param actionEvent the action that occurs.
     */
    public void goToTravel(ActionEvent actionEvent) {
        player.setCurrentSystem(GameData.getObject().getUniverse().getSystem().get(0));
        mediator.loadScreen("Please Select a System", "../view/Travel.fxml");
    }

    /**
     * This goes to Ship yard.
     * @param actionEvent the action that occurs.
     */
    public void goToShipyard(ActionEvent actionEvent) {
        if (currentSystem.getTechLevel() > 4) {
            mediator.loadScreen("The Shipyard", "../view/Shipyard.fxml");
        } else {
            showMessage("Too low tech level", new Text("This planet does not have a high enough tech level to visit the Shipyard!"));
        }
    }

    /**
     * This is when the game is saved.
     * @param actionEvent the action that occurs.
     */
    public void saveGame(ActionEvent actionEvent)
    {
        ObjectOutputStream oos = null;
        ObjectOutputStream oosTwo = null;
        try {
            FileOutputStream fout = new FileOutputStream(new File(GameData.getObject().getGameSaveLoc()));
            FileOutputStream foutTwo = new FileOutputStream(new File(GameData.getObject().getSystSaveLoc()));
            oos = new ObjectOutputStream(fout);
            oosTwo = new ObjectOutputStream(foutTwo);

            Player currPlayer = GameData.getObject().getPlayer();
            Universe oldUni = GameData.getObject().getUniverse();

            oos.writeObject(currPlayer);
            oosTwo.writeObject(oldUni);
            fout.close();
            foutTwo.close();
            oos.close();
            oosTwo.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }

            if (oosTwo != null) {
                try {
                    oosTwo.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        showMessage("Save Game.", new Text(25, 25, "The game has been saved!"));
    }

    /**
     * The message is shown.
     * @param title the titlethat occurs.
     * @param text the text that occurs.
     */
    private void showMessage(String title, Text text)
    {
        Stage dialog = new Stage();
        dialog.setTitle(title);
        dialog.initStyle(StageStyle.UTILITY);
        javafx.scene.control.Button cancelButton = new javafx.scene.control.Button("OK");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        VBox dialogHBox = new VBox();
        dialogHBox.getChildren().addAll(text, cancelButton);
        dialogHBox.setAlignment(Pos.CENTER);
        dialogHBox.setSpacing(20);
        Scene scene = new Scene(dialogHBox, 550, 150);
        dialog.setHeight(150);
        dialog.setWidth(600);
        dialog.setScene(scene);
        dialog.show();
    }
}
