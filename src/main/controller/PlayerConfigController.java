package main.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.model.GameData;
import main.model.Player;
import main.model.Ship;
import main.model.Universe;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

/**
 * Created by drew on 9/12/14.
 */
public class PlayerConfigController implements ScreenController, Initializable {

    //CHECKSTYLE:OFF
    // <editor-fold desc="FXML Values">
    @FXML private Text pilotSkillValue;
    @FXML private Text fighterSkillValue;
    @FXML private Text traderSkillValue;
    @FXML private Text engineerSkillValue;
    @FXML private Text investorSkillValue;
    @FXML private Text count;
    @FXML private TextField playerName;

    @FXML private Button decreasePilotSkill;
    @FXML private Button increasePilotSkill;
    @FXML private Button decreaseFighterSkill;
    @FXML private Button increaseFighterSkill;
    @FXML private Button decreaseTraderSkill;
    @FXML private Button increaseTraderSkill;
    @FXML private Button decreaseEngineerSkill;
    @FXML private Button increaseEngineerSkill;
    @FXML private Button decreaseInvestorSkill;
    @FXML private Button increaseInvestorSkill;
    // </editor-fold>

    private Button[] incButtons;
    private Button[] decButtons;

    private Mediator mediator;

    private int[] skillValues = new int[5];
    //CHECKSTYLE:ON

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        incButtons = new Button[] {increasePilotSkill, increaseFighterSkill, increaseTraderSkill, increaseEngineerSkill, increaseInvestorSkill};
        decButtons = new Button[] {decreasePilotSkill, decreaseFighterSkill, decreaseTraderSkill, decreaseEngineerSkill, decreaseInvestorSkill};
    }

    /**
     * Loads the mediator.
     * @param mediator the mediator
     */
    public void loadMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    /**
     * Adds the values.
     * @return the sum of the skills.
     */
    public int sum() {
        return IntStream.of(skillValues).sum();
    }


    /**
     * The changing of skill values.
     * @param actionEvent the button click being called.
     */
    public void changeSkillValue(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();

        if (Arrays.asList(incButtons).contains(source)) {
            int index = Arrays.asList(incButtons).indexOf(source);
            if (skillValues[index] < 10 && sum() < 15) {
                skillValues[index]++;
            }
        } else if (Arrays.asList(decButtons).contains(source)) {
            int index = Arrays.asList(decButtons).indexOf(source);
            if (skillValues[index] > 0) {
                skillValues[index]--;
            }
        }

        updateAll();
    }

    /**
     * Updates all the skills.
     */
    public void updateAll() {
        pilotSkillValue.setText(skillValues[0] + "");
        fighterSkillValue.setText(skillValues[1] + "");
        traderSkillValue.setText(skillValues[2] + "");
        engineerSkillValue.setText(skillValues[3] + "");
        investorSkillValue.setText(skillValues[4] + "");
        count.setText((15 - sum()) + " Remaining");

        for (int i = 0; i < 5; i++) {
            setDecreaseButtonVisibility(decButtons[i], skillValues[i]);
            setIncreaseButtonVisibility(incButtons[i], skillValues[i]);
        }
    }

    /**
     * Sets the button visibility when the value is gone.
     * @param b the button.
     * @param value the value.
     */
    public void setDecreaseButtonVisibility(Button b, int value) {
        if (value == 0) {
            b.setVisible(false);
        } else {
            b.setVisible(true);
        }
    }

    /**
     * Increases the button visibility.
     * @param b the button.
     * @param value the value.
     */
    public void setIncreaseButtonVisibility(Button b, int value) {
        if (value == 10 || sum() == 15) {
            b.setVisible(false);
        } else {
            b.setVisible(true);
        }
    }

    /**
     * The submit button.
     * @param actionEvent the button click being called.
     */
    public void submit(ActionEvent actionEvent) {
        if (!playerName.getText().equals("")) {
            GameData.getObject().createUniverse(
                    new Universe()
            );
            GameData.getObject().createPlayer(
                    new Player(playerName.getText(), skillValues, GameData.getObject().getUniverse().getSystem().get(0), new Ship())
            );
            mediator.loadScreen("Space Trader!", "../view/CurrentSystem.fxml");
        } else {
            nameErrorDialog();
        }
    }

    /**
     * The error dialog for name.
     */
    private void nameErrorDialog() {
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
        Text errorText = new Text(25, 25, "You must choose a name for your character.");
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
     * The values are reset.
     * @param actionEvent the button click being called.
     */
    public void resetValues(ActionEvent actionEvent) {
        Arrays.fill(skillValues, 0);
        updateAll();
    }

    /**
     * The back button is pushed.
     * @param actionEvent the button click being called.
     */
    public void back(ActionEvent actionEvent) {
        mediator.loadScreen("Start Screen", "../view/manageGameScreen.fxml");
    }
}
