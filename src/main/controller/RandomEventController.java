package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.model.GameData;
import main.model.Player;
import main.model.SolarSystem;
import java.util.ArrayList;
import javafx.scene.text.Text;

/**
 * Created by nathan on 10/19/14.
 */
public class RandomEventController implements ScreenController {
    //CHECKSTYLE:OFF
    @FXML private Text messagePirate;

    private Mediator mediator;
    private Player player = GameData.getObject().getPlayer();
    private SolarSystem currentSystem;
    private int currentFuel;
    private ArrayList<SolarSystem> universe;
    //CHECKSTYLE:ON

    /**
     * Loads a mediator.
     * @param m mediator
     */
    public void loadMediator(Mediator m)
    {
        mediator = m;
    }

    /**
     * OK's the random event.
     * @param actionEvent the button click being called.
     */
    public void okRandEvent(ActionEvent actionEvent)
    {
        //update();
        mediator.loadScreen("Current System!", "../view/CurrentSystem.fxml");
    }


}
