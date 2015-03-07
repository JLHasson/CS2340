package main.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.model.GameData;
import main.model.Player;
import main.model.SolarSystem;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.ResourceBundle;

/**
 * Created by nathan on 11/6/14.
 */
public class GadgetController implements ScreenController, Initializable {

    //CHECKSTYLE:OFF
    @FXML private Text yourCreditsText;
    @FXML private Text shieldPrice;
    @FXML private Text weaponsPrice;
    @FXML private Text cargoPrice;
    @FXML private Text healthPrice;

    @FXML private Button increaseShield;
    @FXML private Button increaseWeapons;
    @FXML private Button increaseCargo;
    @FXML private Button increaseHealth;

    @FXML private Button decreaseShield;
    @FXML private Button decreaseWeapons;
    @FXML private Button decreaseCargo;
    @FXML private Button decreaseHealth;

    @FXML private Text shieldNum;
    @FXML private Text weaponsNum;
    @FXML private Text cargoNum;
    @FXML private Text healthNum;
    @FXML private Text totalCostText;

    private ArrayList<Button> incButtons;
    private ArrayList<Button> decButtons;
    private ArrayList<Text> prices;
    private ArrayList<Integer> numInCart;
    private ArrayList<Text> quantities;
    private ArrayList<Integer> numPrices;


    private int moneyLost = 0;
    private int sumNumInCart = 0;

    private String currMon = "Current Money:";
    private Mediator mediator;
    private Player player = GameData.getObject().getPlayer();
    //CHECKSTYLE:ON

    public void loadMediator(Mediator m)
    {
        this.mediator = m;
    }

    public void initialize(URL location, ResourceBundle resourceBundle) {
        incButtons = new ArrayList<>(Arrays.asList(increaseWeapons, increaseShield, increaseCargo, increaseHealth));
        decButtons = new ArrayList<>(Arrays.asList(decreaseWeapons, decreaseShield, decreaseCargo, decreaseHealth));
        quantities = new ArrayList<>(Arrays.asList(weaponsNum, shieldNum, cargoNum, healthNum));
        numInCart = new ArrayList<>(Arrays.asList(0, 0, 0, 0));
        numPrices = new ArrayList<>();

        prices = new ArrayList<>(Arrays.asList(weaponsPrice, shieldPrice, cargoPrice, healthPrice));

        yourCreditsText.setText("" + player.getMoney());

        SolarSystem currSys = player.getCurrentSystem();

        if(currSys.getTechLevel() <= 6)
        {
            incButtons.get(2).setVisible(false);
            decButtons.get(2).setVisible(false);
            incButtons.get(3).setVisible(false);
            decButtons.get(3).setVisible(false);
            for(int i = 0; i < 2; i++)
            {
                int val = (100 + ((int) (Math.random() * 100)));
                prices.get(i).setText("" + val);
                numPrices.add(val);
            }
        }
        else if(currSys.getTechLevel() <= 7)
        {
            incButtons.get(3).setVisible(false);
            decButtons.get(3).setVisible(false);
            for(int i = 0; i < 3; i++)
            {
                int val = (100 + ((int) (Math.random() * 100)));
                prices.get(i).setText("" + val);
                numPrices.add(val);
            }
        }
    }

    public void back()
    {
        mediator.loadScreen("Space Trader!", "../view/Shipyard.fxml");
    }

    public void updateMoney(int mon)
    {
        player.changeMoney(-mon);
    }

    public void changeCart(ActionEvent e)
    {
        Object source = e.getSource();

        if(decButtons.contains(source))
        {
            int i = decButtons.indexOf(source);
            if(numInCart.get(i) >= 1)
            {
                numInCart.set(i, (numInCart.get(i) - 1));
                moneyLost -= (numPrices.get(i));
                sumNumInCart--;
                quantities.get(i).setText("" + numInCart.get(i));
                totalCostText.setText("" + moneyLost);
            }
        }
        else if(incButtons.contains(source))
        {
            int i = incButtons.indexOf(source);
            numInCart.set(i, (numInCart.get(i) + 1));
            moneyLost += (numPrices.get(i));
            sumNumInCart++;
            quantities.get(i).setText("" + numInCart.get(i));
            totalCostText.setText("" + moneyLost);
        }
    }

    public void confirm()
    {
        if(moneyLost > player.getMoney())
        {
            showMessage("Not Enough Money", new Text("You do not have enough money to make this purchase!"));
        }
        else if(sumNumInCart > player.getShip().getGadgetSlots())
        {
            showMessage("Not Enough Cargo Space", new Text("You do not have enough cargo space to make this purchase!"));
        }
        else
        {
            updateMoney(moneyLost);
            yourCreditsText.setText("" + player.getMoney());

            System.out.println(player.getShip().getGadgetSlots());

            player.getShip().setCargoSize(player.getShip().getMaxCargo() + numInCart.get(2));
            player.setFightingSkill(player.getFightingSkill() + (2 * numInCart.get(0)));
            player.getShip().setHealth((20 * numInCart.get(1)));
            player.getShip().setMaxHealth(numInCart.get(3) * 5);
            player.getShip().changeGadgetSlots(-sumNumInCart);

            System.out.println(player.getShip().getGadgetSlots());
            System.out.println(player.getShip().getMaxCargo());
            System.out.println(player.getFightingSkill());
            System.out.println(player.getShip().getHealth());
            System.out.println(player.getShip().getMaxHealth());

            for(Text t: quantities)
            {
                t.setText("0");
            }
            totalCostText.setText("0");
            for(Integer i: numInCart)
            {
                i = 0;
            }
            sumNumInCart = 0;
        }
    }

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
