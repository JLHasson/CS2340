package main.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.model.GameData;
import main.model.Player;
import main.model.Universe;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * Created by Lance Hasson on 9/11/2014.
 *
 * Manage Game Screen Controller class
 *
 * This class handles all actions on the manageGameScreen.fxml file.
 * Will send the player to the player config screen, or back to the start
 * menu based on the input it recieves.
 */
public class ManageGameScreenController implements ScreenController {

    /**
     * Mediator ref.
     */
    private Mediator mediator;

    /**
     * Title.
     */
    private String title = "Space Trader!";

    /**
     * Handles the action of the newGameAction fxml button.
     * @param actionEvent the action that occurs.
     */
    public void handleNewGameAction(ActionEvent actionEvent) {
        mediator.loadScreen("New Game", "../view/playerConfig.fxml");
    }

    /**
     * Gets the mediator object.
     * @param mediator singleton mediator instance
     */
    @Override
    public void loadMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    /**
     * Handles the action of the back fxml button.
     * @param actionEvent the action that occurs.
     */
    public void handleBack(ActionEvent actionEvent) {
        mediator.loadScreen(title, "../view/startScreen.fxml");
    }
    /**
     * Handles the action of loading the game from serializable.
     * @param actionEvent the action that occurs.
     *
     */
    public void loadGame(ActionEvent actionEvent)
    {
        try {

            FileInputStream fin = new FileInputStream(new File(GameData.getObject().getGameSaveLoc()));


            ObjectInputStream ois = new ObjectInputStream(fin);


            Player savedPlayer = (Player) ois.readObject();

            GameData.getObject().createPlayer(savedPlayer);

            ois.close();
            fin.close();

            FileInputStream finTwo = new FileInputStream(new File(GameData.getObject().getSystSaveLoc()));
            ObjectInputStream oisTwo = new ObjectInputStream(finTwo);

            Universe oldUni = (Universe) oisTwo.readObject();

            GameData.getObject().createUniverse(oldUni);
            System.out.println(oldUni);

            oisTwo.close();
            finTwo.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        showMessage("Load Game.", new Text(25, 25, "The last saved game has been Loaded!"));
        mediator.loadScreen(title, "../view/CurrentSystem.fxml");
    }

    /*public void saveGame(ActionEvent actionEvent)
    {
        try{

            FileOutputStream fout = new FileOutputStream("../../savegamefiles/player.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(GameData.getObject().getPlayer());
            oos.close();
            showMessage("Save Game.",new Text(25, 25, "The game has been saved!"));

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }*/

    /**
     * This shows the message.
     * @param messagetitle title of the message.
     * @param text  text for message.
     */
    private void showMessage(String messagetitle, Text text)
    {
        Stage dialog = new Stage();
        dialog.setTitle(messagetitle);
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
