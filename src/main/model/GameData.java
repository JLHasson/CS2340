package main.model;

import java.util.HashMap;

/**
 * Created by drew on 9/11/14.
 *
 * Game Data
 *
 * Class that holds all data used in the game. Instantiates all trade goods,
 * holds the player object and the universe.
 */
public class GameData {

    private static GameData _obj;
    private Player player;
    private Universe universe = new Universe(); //"= new Universe();" has to be here in order for saving and loading the game to work.
                                                //Im not sure why yet.
    public static final HashMap<String, TradeGood> TRADEGOODS = new HashMap<>();
    private static final String[] GOODNAMES = {"Water", "Furs", "Food", "Ore", "Games", "FireArms", "Medicine", "Machines", "Narcotics", "Robots"};
    static {
        TRADEGOODS.put(GOODNAMES[0], new TradeGood(GOODNAMES[0], 0, 0, 2, 30, 3, 4, "DROUGHT", Universe.Resources.LOTSOFWATER, Universe.Resources.DESERT, 30, 50));
        TRADEGOODS.put(GOODNAMES[1], new TradeGood(GOODNAMES[1], 0, 0, 0, 250, 10, 10, "COLD", Universe.Resources.RICHFAUNA, Universe.Resources.LIFELESS, 230, 280));
        TRADEGOODS.put(GOODNAMES[2], new TradeGood(GOODNAMES[2], 1, 0, 1, 100, 5, 5, "CROPFAIL", Universe.Resources.RICHSOIL, Universe.Resources.POORSOIL, 90, 160));
        TRADEGOODS.put(GOODNAMES[3], new TradeGood(GOODNAMES[3], 2, 2, 3, 350, 20, 10, "WAR", Universe.Resources.MINERALRICH, Universe.Resources.MINERALPOOR, 350, 420));
        TRADEGOODS.put(GOODNAMES[4], new TradeGood(GOODNAMES[4], 3, 1, 6, 250, -10, 5, "BOREDOM", Universe.Resources.ARTISTIC, null, 160, 270));
        TRADEGOODS.put(GOODNAMES[5], new TradeGood(GOODNAMES[5], 3, 1, 5, 1250, -75, 100, "WAR", Universe.Resources.WARLIKE, null, 600, 1100));
        TRADEGOODS.put(GOODNAMES[6], new TradeGood(GOODNAMES[6], 4, 1, 6, 650, -20, 10, "PLAGUE", Universe.Resources.LOTSOFHERBS, null, 400, 700));
        TRADEGOODS.put(GOODNAMES[7], new TradeGood(GOODNAMES[7], 4, 3, 5, 900, -30, 5, "LACKOFWORKERS", null, null, 600, 800));
        TRADEGOODS.put(GOODNAMES[8], new TradeGood(GOODNAMES[8], 5, 0, 5, 3500, -125, 150, "BOREDOM", Universe.Resources.WEIRDMUSHROOMS, null, 2000, 3000));
        TRADEGOODS.put(GOODNAMES[9], new TradeGood(GOODNAMES[9], 6, 4, 7, 5000, -150, 100, "LACKOFWORKERS", null, null, 3500, 5000));
    }

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    
    private String gameSaveLoc = "/home/nathan/IdeaProjects/blanj/out/production/blanj/savegamefiles/gameFile.txt";
    private String systSaveLoc = "/home/nathan/IdeaProjects/blanj/out/production/blanj/savegamefiles/systemFile.txt";

    private GameData() {

    }

    /**
     * If GameData object exists then return that object, if not create one.
     * @return the GameData object
     */
    public static GameData getObject() {
        if (_obj == null) {
            _obj = new GameData();
        }
        return _obj;
    }

    /**
     * Gets the game save location.
     * @return the string path for saving the game
     */
    public String getGameSaveLoc()
    {
        return gameSaveLoc;
    }

    /**
     * Creates the player object and stores it in the GameData instance.
     * @param p the player to store
     */
    public void createPlayer(Player p) {
        System.out.println("Player created successfully");
        player = p;
    }

    /**
     * Creates the universe object and stores it in the GameData instance.
     * @param u the universe to store
     */
    public void createUniverse(Universe u) {
        universe = u;
        System.out.println(universe);
    }

    /**
     * Gets the Universe.
     * @return the universe instance
     */
    public Universe getUniverse()
    {
        return universe;
    }

    /**
     * Gets the Player.
     * @return the player instance
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the hashmap of trade goods.
     * @return hashmap containing all trade goods
     */
    public HashMap<String, TradeGood> getTradegoods()
    {
        return TRADEGOODS;
    }

    /**
     * Gets the system save location.
     * @return syst save loc
     */
    public String getSystSaveLoc()
    {
        return systSaveLoc;
    }

    /**
     * Returns an array holding all of the good names.
     * @return a copy of the good names array
     */
    public static String[] getGoodnames() {
        String[] toReturn = new String[GOODNAMES.length];
        System.arraycopy(GOODNAMES, 0, toReturn, 0, GOODNAMES.length);
        return toReturn;
    }
}
