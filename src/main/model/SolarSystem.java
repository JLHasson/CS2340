package main.model;

import main.model.Universe.Resources;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;

/**
 * Created by JoelAnderson on 9/18/14.
 */
public class SolarSystem implements Serializable {

    private String name;
    private int techLevel;
    private Resources resources;
    private Point location;

    public SolarSystem(String name, Point loc, int techLevel, Resources resources) {
        this.name = name;
        this.location = loc;
        this.techLevel = techLevel;
        this.resources = resources;
    }

    private int numberRandomizer(int upperRestraint){
        Random random = new Random();
        return random.nextInt(upperRestraint + 1);
    }

    //(the base price) + (the IPL * (Planet Tech Level - MTLP)) + (variance)
    public int calculateTradeGoodPrice(String goodName) {
        TradeGood good = GameData.TRADEGOODS.get(goodName);
        int price = (good.getBasePrice()) +
                    (good.getIPL() * (techLevel - good.getMTLP()) +
                    (good.getBasePrice() * calculateVarience(good.getVar())));
        if (good.getER() == resources) {
            price = price * 2;
        } else if (good.getCR() == resources) {
            price = price / 2;
        }
        return price;
    }

    public int calculateVarience(int var) {
        Random rand = new Random();
        int sign = rand.nextBoolean() ? 1 : -1;
        return sign * rand.nextInt(var + 1);
    }

    public int getTechLevel() {
        return techLevel;
    }

    public String getName() {
        return name;
    }

    public Resources getResources() {
        return resources;
    }

    public Point getLocation() { return location; }

    public String toString() {
        return "SolarSystem " + name + " at (" +
                Double.toString(location.getX()) + ", " +
                Double.toString(location.getY()) + ")\nTech Level: " +
                techLevel + "\nResources: " +
                resources;
    }

}
