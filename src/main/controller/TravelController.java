package main.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.model.GameData;
import main.model.Player;
import main.model.SolarSystem;

import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by Lance Hasson on 10/9/2014.
 *
 * Handles displaying the universe graphically and allows the player
 * to travel to different systems.
 */
public class TravelController implements ScreenController, Initializable {

    /**
     * The Universe Pane.
     */
    @FXML private Pane universePane;
    /**
     * The text Pane.
     */
    @FXML private Text hoverInfo;

    /**
     * The Space Trader title.
     */
    private String spcTrader = "Space Trader";
    /**
     * The current view.
     */
    private String currSysView = "../view/CurrentSystem.fxml";

    /**
     * The mediator Ref.
     */
    private Mediator mediator;
    /**
     * The player ref.
     */
    private Player player;
    /**
     * The current sys.
     */
    private SolarSystem currentSystem;
    /**
     * The current fuel.
     */
    private int currentFuel;
    /**
     * The universe.
     */
    private ArrayList<SolarSystem> universe;
    /**
     * Set rand testing mode.
     */
    private boolean randTestingMode = false;

    @Override
    public void loadMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    /**
     * Handles the movement of the ship from planet
     * to planet as well as random events.
     * @param location location of res.
     */
    public void initialize(URL location, ResourceBundle resources) {
        player = GameData.getObject().getPlayer();
        currentSystem = player.getCurrentSystem();
        currentFuel = player.getShip().getFuel();
        universe = GameData.getObject().getUniverse().getSystem();


        universePane.setStyle("-fx-background-color: black;");
        universePane.setPrefWidth(GameData.WIDTH);
        universePane.setPrefHeight(GameData.HEIGHT);

        Circle currentCircle = new Circle(3, Color.GREEN);
        currentCircle.relocate(currentSystem.getLocation().getX(), currentSystem.getLocation().getY());
        currentCircle.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hoverInfo.setText("You Are Here!");
            }
        });
        currentCircle.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hoverInfo.setText("");
            }
        });
        currentCircle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediator.loadScreen(spcTrader, currSysView);
            }
        });
        universePane.getChildren().add(currentCircle);

        for (SolarSystem s: universe) {
            if (s == currentSystem) { continue; }
            Circle sys = new Circle(3, Color.BLUE);
            sys.relocate(s.getLocation().getX(), s.getLocation().getY());
            Tooltip t = new Tooltip(s.getName() + " : " + s.getLocation().toString());
            Tooltip.install(sys, t);
            sys.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    int dist = calcDistance(s.getLocation(), currentSystem.getLocation());
                    SolarSystem oldSystem = currentSystem;
                    if (dist < currentFuel) {
                        player.setCurrentSystem(s);
                        player.getShip().setFuel(currentFuel - dist);

                        if (System.currentTimeMillis() % 5 == 0 || randTestingMode)
                        {

                            int num = (int) (Math.random() * 3);

                            String screen;

                            if (num == 0)
                            {
                                screen = "../view/SolarStorm.fxml";
                                player.getShip().setFuel(currentFuel + dist);
                                player.setCurrentSystem(oldSystem);
                            }
                            else if (num == 1)
                            {
                                screen = "../view/Pirate.fxml";
                                player.getShip().setHealth(-25);
                                player.changeMoney(-100);
                            }
                            else
                            {
                                screen = "../view/Asteroid.fxml";
                                player.getShip().setHealth(-25);
                            }
                            mediator.loadScreen("Random Event!", screen);
                        }
                        else {
                            mediator.loadScreen(spcTrader, currSysView);
                        }
                    } else {
                        Text errorText = new Text(25, 25, "You don't have enough fuel to travel to that system.");
                        showMessage("Not Enough Fuel!", errorText);
                    }
                }
            });
            sys.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    hoverInfo.setText("The " + s.getName() + " System.\nLocation: "
                            + s.getLocation().getX() + ", " + s.getLocation().getY() + "\n"
                            + "Tech Level: " + s.getTechLevel() + "\n"
                            + "Resources: " + s.getResources()
                            + "\nCost of Travel: " + calcDistance(s.getLocation(), currentSystem.getLocation())
                            + "\nCurrent Fuel: " + currentFuel
                            + "\nClick to travel!");
                }
            });
            sys.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    hoverInfo.setText("");
                }
            });
            universePane.getChildren().add(sys);
        }
    }

    /**
     * This is to calculate dist.
     * @param a the first point.
     * @param b the second point.
     * @return the calculated dist
     */
    private int calcDistance(Point a, Point b) {
        return (int) Math.sqrt((a.getX() - b.getX()) * (a.getX() - b.getX()) + (a.getY() - b.getY()) * (a.getY() - b.getY()));
    }

    /**
     * This shows a message.
     * @param title the title.
     * @param text the text.
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