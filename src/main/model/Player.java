package main.model;

import java.io.Serializable;

/**
 * Created by drew on 9/11/14.
 *
 * Player
 *
 * Holds all skill levels, ship, money, and mercenaries for the player.
 */
public class Player implements SkilledIndividual, Serializable {

    private int pilotingSkill;
    private int fightingSkill;
    private int tradingSkill;
    private int engineeringSkill;
    private int investingSkill;
    private SolarSystem currentSystem;
    private Ship myShip;
    private int money;


    /**
     * initializes player object.
     * @param name player name
     * @param piloting piloting skill
     * @param fighting fighting skill
     * @param trading trading skill
     * @param engineering engineering skill
     * @param investing investing
     * @param startSys starting system
     * @param startShip starting ship
     */
    public Player(String name, int piloting, int fighting, int trading, int engineering, int investing, SolarSystem startSys, Ship startShip) {
        pilotingSkill = piloting;
        fightingSkill = fighting;
        tradingSkill = trading;
        engineeringSkill = engineering;
        investingSkill = investing;
        money = 1000;
        currentSystem = startSys;
        myShip = startShip;
    }

    /**
     * initializes player object.
     * @param name Player name
     * @param skills array of player skills
     * @param startSys starting system
     * @param startShip starting ship
     */
    public Player(String name, int[] skills, SolarSystem startSys, Ship startShip) {
        pilotingSkill = skills[0];
        fightingSkill = skills[1];
        tradingSkill =  skills[2];
        engineeringSkill =  skills[3];
        investingSkill =  skills[4];
        money = 1000;
        currentSystem = startSys;
        myShip = startShip;
    }

    @Override
    public int getPilotingSkill() {
        return pilotingSkill;
    }

    @Override
    public int getFightingSkill() {
        return fightingSkill;
    }

    @Override
    public int getTradingSkill() {
        return tradingSkill;
    }

    @Override
    public int getEngineeringSkill() {
        return engineeringSkill;
    }

    @Override
    public int getInvestingSkill() {
        return investingSkill;
    }

    /**
     * Set the player's current system as the new system.
     * @param newSystem the new system
     */
    public void setCurrentSystem(SolarSystem newSystem)
    {
        currentSystem = newSystem;
    }

    /**
     * Set the players current ship.
     * @param newShip the new ship
     */
    public void setShip(Ship newShip) {
        this.myShip = newShip;
    }

    /**
     * Gets the current system.
     * @return the current system
     */
    public SolarSystem getCurrentSystem()
    {
        return currentSystem;
    }

    /**
     * Gets the current ship.
     * @return the current ship
     */
    public Ship getShip()
    {
        return myShip;
    }

    /**
     * Gets the current money.
     * @return the current amount of money
     */
    public int getMoney() {
        return money;
    }

    /**
     * Updates the players current money value.
     * @param amt the amt to change the player's money by
     */
    public void changeMoney(int amt) {
        if (money + amt < 0) {
            throw new IllegalArgumentException("Cannot have negative money");
        }

        money += amt;
    }

    public void setFightingSkill(int s)
    {
        fightingSkill = s;
    }

}
