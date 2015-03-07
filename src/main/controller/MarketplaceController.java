package main.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.model.GameData;
import main.model.Player;
import main.model.SolarSystem;
import main.model.TradeGood;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Created by Benjamin_Wilson on 10/2/14.
 *
 * Marketplace Controller Class
 *
 * The market place controller handles all the interaction with the marketplace.
 * This class takes in information from the player object and uses that to
 * dynamically load the trade goods for the current system, along with their
 * quantities and prices.
 */
public class MarketplaceController implements ScreenController, Initializable {

    // CHECKSTYLE:OFF
    private Mediator mediator;

    @FXML private Button increaseWater;
    @FXML private Button decreaseWater;
    @FXML private Button increaseFurs;
    @FXML private Button decreaseFurs;
    @FXML private Button increaseFood;
    @FXML private Button decreaseFood;
    @FXML private Button increaseOre;
    @FXML private Button decreaseOre;
    @FXML private Button increaseGames;
    @FXML private Button decreaseGames;
    @FXML private Button increaseGuns;
    @FXML private Button decreaseGuns;
    @FXML private Button increaseMed;
    @FXML private Button decreaseMed;
    @FXML private Button increaseMach;
    @FXML private Button decreaseMach;
    @FXML private Button increaseNarc;
    @FXML private Button decreaseNarc;
    @FXML private Button increaseBots;
    @FXML private Button decreaseBots;

    @FXML private Button switchSellTrade;

    @FXML private Text marketWaterValue;
    @FXML private Text marketFursValue;
    @FXML private Text marketFoodValue;
    @FXML private Text marketOreValue;
    @FXML private Text marketGamesValue;
    @FXML private Text marketGunsValue;
    @FXML private Text marketMedValue;
    @FXML private Text marketMachValue;
    @FXML private Text marketNarcValue;
    @FXML private Text marketBotsValue;

    @FXML private Text waterQuantity;
    @FXML private Text fursQuantity;
    @FXML private Text foodQuantity;
    @FXML private Text oreQuantity;
    @FXML private Text gamesQuantity;
    @FXML private Text gunsQuantity;
    @FXML private Text medQuantity;
    @FXML private Text machQuantity;
    @FXML private Text narcQuantity;
    @FXML private Text botsQuantity;

    @FXML private Text waterPrice;
    @FXML private Text fursPrice;
    @FXML private Text foodPrice;
    @FXML private Text orePrice;
    @FXML private Text gamesPrice;
    @FXML private Text gunsPrice;
    @FXML private Text medPrice;
    @FXML private Text machPrice;
    @FXML private Text narcPrice;
    @FXML private Text botsPrice;

    @FXML private Text totalCostText;
    @FXML private Text creditsAfterPurchase;
    @FXML private Text yourCreditsText;


    private Button[] incButtons;
    private Button[] decButtons;

    private Text[] goodQuantitiesText;
    private Text[] cartValuesText;
    private Text[] goodPricesText;

    private int[] cartValues = new int[10];
    private int[] goodQuantities = new int[10];
    private int[] goodPrices = new int[10];
    private int[] goodQuantitiesStorage = new int[10];

    private int totalCost;
    private int playerMoney;
    private int moneyAfterTrade;

    private boolean buying = true;
    private Player player;
    private SolarSystem currSys;

    private String zero = "0";
    // CHECKSTYLE:ON

    /**
     * JavaFX default function for dynamically initializing values on screen.
     * Sets the text for the quantities, prices, and cart amounts. Also
     * sets up the buttons used to increasing and decreasing your cart
     * values.
     * @param location the location of the res.
     * @param resourceBundle the res bundle.
     */
    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        incButtons = new Button[] {increaseWater, increaseFurs, increaseFood, increaseOre,
            increaseGames, increaseGuns, increaseMed, increaseMach, increaseNarc, increaseBots};
        decButtons = new Button[] {decreaseWater, decreaseFurs, decreaseFood, decreaseOre, decreaseGames,
            decreaseGuns, decreaseMed, decreaseMach, decreaseNarc, decreaseBots};
        goodQuantitiesText = new Text[] {waterQuantity, fursQuantity, foodQuantity, oreQuantity, gamesQuantity,
            gunsQuantity, medQuantity, machQuantity, narcQuantity, botsQuantity};
        cartValuesText = new Text[] {marketWaterValue, marketFursValue, marketFoodValue, marketOreValue,
            marketGamesValue, marketGunsValue, marketMedValue, marketMachValue, marketNarcValue, marketBotsValue};
        goodPricesText = new Text[] {waterPrice, fursPrice, foodPrice, orePrice, gamesPrice, gunsPrice,
            medPrice, machPrice, narcPrice, botsPrice};
        player = GameData.getObject().getPlayer();
        playerMoney = player.getMoney();
        yourCreditsText.setText(playerMoney + "");
        currSys = player.getCurrentSystem();
        String[] marketplaceKeys = GameData.getObject().getTradegoods().keySet().toArray(new String[10]);
        for (int i = 0; i < marketplaceKeys.length; i++)
        {
            TradeGood currGood = GameData.getObject().getTradegoods().get(marketplaceKeys[i]);
            goodPrices[i] = currGood.getBasePrice() + (currGood.getIPL()
                    * (currSys.getTechLevel() - currGood.getMTLP())) + ((int) (Math.random() * (currGood.getVar() + 1)));
            System.out.println(currGood.getBasePrice());
            goodPricesText[i].setText(goodPrices[i] + "");
            int techLevelDiff = currSys.getTechLevel() - currGood.getMTLP();
            goodQuantities[i] = (techLevelDiff >= 0) ? techLevelDiff * (10 - i) * (int) ((Math.random() * 25) + 1) : 0;
            goodQuantitiesText[i].setText(goodQuantities[i] + "");
        }
        updateAll();
    }

    /**
     * Gets mediator instance.
     * @param mediator singleton mediator object
     */
    public void loadMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    /**
     * Determines which plus or minus button is being clicked,
     * and adjusts the number of items in cart.
     *
     * @param actionEvent the button click being called.
     */
    public void changeCartValue(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if (Arrays.asList(incButtons).contains(source)) {
            int index = Arrays.asList(incButtons).indexOf(source);
            if (buying) {
                if (playerMoney - totalCost - goodPrices[index] >= 0) {
                    totalCost = totalCost + goodPrices[index];
                    cartValues[index]++;
                    goodQuantities[index]--;
                    updateAll();
                } else {
                    notEnoughMoney();
                }
            } else {
                totalCost = totalCost + goodPrices[index];
                cartValues[index]++;
                goodQuantities[index]--;
                updateAll();
            }
        } else if (Arrays.asList(decButtons).contains(source)) {
            int index = Arrays.asList(decButtons).indexOf(source);
            if (cartValues[index] > 0) {
                totalCost = totalCost - goodPrices[index];
                cartValues[index]--;
                goodQuantities[index]++;
                updateAll();
            }
        }
    }

    /**
     * Updates the credit counts, button visibilities,
     * and text fields for the cart and store.
     */
    public void updateAll() {
        for (int i = 0; i < 10; ++i) {
            cartValuesText[i].setText(cartValues[i] + "");
            goodQuantitiesText[i].setText(goodQuantities[i] + "");
            setDecreaseButtonVisibility(decButtons[i], cartValues[i]);
            setIncreaseButtonVisibility(incButtons[i], goodQuantities[i]);
        }
        totalCostText.setText(totalCost + "");
        if (buying) {
            moneyAfterTrade = playerMoney - totalCost;
        } else {
            moneyAfterTrade = playerMoney + totalCost;
        }
        yourCreditsText.setText(playerMoney + "");
        creditsAfterPurchase.setText(moneyAfterTrade + "");
    }

    /**
     * Displays a dialog box informing the user
     * that he does not have enough money.
     */
    public void notEnoughMoney() {
        Stage dialog = new Stage();
        dialog.setTitle("Not Enough Money!");
        dialog.initStyle(StageStyle.UTILITY);
        Button cancelButton = new Button("OK");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        Text errorText = new Text(25, 25, "You don't have enough money.");
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

    /**
     * Checks to see if the minus button should be visible
     * or invisible.
     * @param b the button
     * @param inCart in cart.
     */
    public void setDecreaseButtonVisibility(Button b, int inCart) {
        if (inCart == 0) {
            b.setVisible(false);
        } else {
            b.setVisible(true);
        }
    }

    /**
     * Checks to see if the plus button should be
     * visible or invisible.
     * @param b the button
     * @param inStock in cart.
     */
    public void setIncreaseButtonVisibility(Button b, int inStock) {
        if (inStock == 0) {
            b.setVisible(false);
        } else {
            b.setVisible(true);
        }
    }

    /**
     * Completes Trade.
     * @param actionEvent the button click being called.
     */
    public void completeTrade(ActionEvent actionEvent)
    {
        if (buying)
        {
            for (int i = 0; i < cartValues.length; i++)
            {
                player.getShip().goodTransaction(GameData.TRADEGOODS.get(GameData.getGoodnames()[i]), cartValues[i]);
                cartValues[i] = 0;
                cartValuesText[i].setText(zero);
                playerMoney = moneyAfterTrade;
                totalCost = 0;
                updateAll();
            }
        }
        else
        {
            for (int i = 0; i < cartValues.length; i++)
            {
                player.getShip().goodTransaction(GameData.TRADEGOODS.get(GameData.getGoodnames()[i]), -cartValues[i]);
                cartValues[i] = 0;
                cartValuesText[i].setText(zero);
                playerMoney = moneyAfterTrade;
                totalCost = 0;
                updateAll();
            }
        }
    }

    /**
     * Switches between selling and trading.
     */
    public void switchSellTrade() {
        buying = !buying;
        if (buying) {
            switchSellTrade.setText("Switch to Sell");
        } else {
            switchSellTrade.setText("Switch to Buy");
        }
        for (int i = 0; i < 10; ++i) {
            if (!buying) {
                if (goodPrices[i] - 50 > 0) {
                    goodPrices[i] = goodPrices[i] - 50;
                } else {
                    goodPrices[i] = 0;
                }
            } else {
                goodPrices[i] = goodPrices[i] + 50;
            }
            goodQuantities[i] = goodQuantities[i] + cartValues[i];
            cartValues[i] = 0;
            goodPricesText[i].setText(goodPrices[i] + "");
        }
        totalCost = 0;
        moneyAfterTrade = 0;
        //playerMoney = player.getMoney();
        if (!buying) {
            goodQuantitiesStorage = goodQuantities;
            for (int i = 0; i < goodQuantities.length; i++)
            {
                goodQuantities[i] = player.getShip().getCargo().get(GameData.TRADEGOODS.get(GameData.getGoodnames()[i]));
            }
        }
        else
        {
            goodQuantities = goodQuantitiesStorage;
        }
        updateAll();
    }

    /**
     * Handles back.
     * @param actionEvent the button click being called.
     */
    public void handleBack(ActionEvent actionEvent) {
        mediator.loadScreen("Space Trader!", "../view/CurrentSystem.fxml");
    }
}