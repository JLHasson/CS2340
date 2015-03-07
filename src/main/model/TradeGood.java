package main.model;

import java.io.Serializable;

/**
 * Created by Lance Hasson on 10/2/2014.
 */
public class TradeGood implements Serializable {

    private String name; //name of the good
    private int MTLP; // minimum tech level to produce
    private int MTLU; // minimum tech level to use
    private int TTP; // tech level which produces the most of this good
    private int BasePrice;
    private int IPL; // price increase per tech level
    private int Var; // variance is the maximum percentage that the price can vary above or below the base
    private String IE; // Radical price increase event, when this even happens on a planet, the price may increase astronomically
    private Universe.Resources CR; // When this condition is present, the price of this resource is unusually low
    private Universe.Resources ER; // When this condition is present, the resource is expensive
    private int MTL; // Min price offered in space trade with random trader (not on a planet)
    private int MTH; // Max price offered in space trade with random trader (not on a planet)

    public TradeGood(String name, int MTLP, int MTLU, int TTP,int BasePrice, int IPL,
                     int Var, String IE, Universe.Resources CR, Universe.Resources ER, int MTL, int MTH) {
        this.name = name;
        this.MTLP = MTLP;
        this.MTLU = MTLU;
        this.TTP = TTP;
        this.BasePrice = BasePrice;
        this.IPL = IPL;
        this.Var = Var;
        this.IE = IE;
        this.CR = CR;
        this.ER = ER;
        this.MTL = MTL;
        this.MTH = MTH;
    }


    public String getName() {
        return name;
    }

    public int getMTLP() {
        return MTLP;
    }

    public int getMTLU() {
        return MTLU;
    }

    public int getTTP() {
        return TTP;
    }

    public int getBasePrice() {
        return BasePrice;
    }

    public int getIPL() {
        return IPL;
    }

    public int getVar() {
        return Var;
    }

    public String getIE() {
        return IE;
    }

    public Universe.Resources getCR() {
        return CR;
    }

    public Universe.Resources getER() {
        return ER;
    }

    public int getMTL() {
        return MTL;
    }

    public int getMTH() {
        return MTH;
    }
}
