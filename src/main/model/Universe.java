package main.model;

import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

/**
 * Created by Lance Hasson on 9/22/2014.
 *
 * Universe Class
 * Handles generation of and storage of all SolarSystems in
 * the universe.
 *
 * Generate highest tech system close to the center, then make
 * several more systems around that center system, using tech
 * levels similar to that of the center system. As you get more outward
 * in the universe the tech levels decrease, but some resources become
 * more available.
 */
public class Universe implements Serializable {

    private ArrayList<SolarSystem> solarSystems;
    private static final int TECH_MAX = 8;
    private static final int RESOURCE_MAX = 13;
    private static final int UNIVERSE_WIDTH = GameData.WIDTH;
    private static final int UNIVERSE_HEIGHT = GameData.HEIGHT;
    private static final Point UNIVERSE_CENTER = new Point(UNIVERSE_WIDTH/2, UNIVERSE_HEIGHT/2);
    private static final int SYSTEM_MAX = 20;
    private int systemCount = 0;
    public static final Map<Integer, String> TechLevel = new HashMap<Integer, String>(){{
        put(0, "PreAgriculture");
        put(1, "Agriculture");
        put(2, "Medieval");
        put(3, "Renaissance");
        put(4, "EarlyIndustrial");
        put(5, "Industrial");
        put(6, "PostIndustrial");
        put(7, "HighTech");
    }};
    public enum Resources {NOSPECIALRESOURCES, MINERALRICH, MINERALPOOR, DESERT, LOTSOFWATER, RICHSOIL,
        POORSOIL, RICHFAUNA, LIFELESS, WEIRDMUSHROOMS, LOTSOFHERBS, ARTISTIC, WARLIKE}

    private static final List<String> SolarSystemName = new ArrayList<String>(Arrays.asList(
            "Acamar",
            "Adahn",        // The alternate personality for The Nameless One in "Planescape: Torment"
            "Aldea",
            "Andevian",
            "Antedi",
            "Balosnee",
            "Baratas",
            "Brax",            // One of the heroes in Master of Magic
            "Bretel",        // This is a Dutch device for keeping your pants up.
            "Calondia",
            "Campor",
            "Capelle",        // The city I lived in while programming this game
            "Carzon",
            "Castor",        // A Greek demi-god
            "Cestus",
            "Cheron",
            "Courteney",    // After Courteney Cox…
            "Daled",
            "Damast",
            "Davlos",
            "Deneb",
            "Deneva",
            "Devidia",
            "Draylon",
            "Drema",
            "Endor",
            "Esmee",        // One of the witches in Pratchett's Discworld
            "Exo",
            "Ferris",        // Iron
            "Festen",        // A great Scandinavian movie
            "Fourmi",        // An ant, in French
            "Frolix",        // A solar system in one of Philip K. Dick's novels
            "Gemulon",
            "Guinifer",        // One way of writing the name of king Arthur's wife
            "Hades",        // The underworld
            "Hamlet",        // From Shakespeare
            "Helena",        // Of Troy
            "Hulst",        // A Dutch plant
            "Iodine",        // An element
            "Iralius",
            "Janus",        // A seldom encountered Dutch boy's name
            "Japori",
            "Jarada",
            "Jason",        // A Greek hero
            "Kaylon",
            "Khefka",
            "Kira",            // My dog's name
            "Klaatu",        // From a classic SF movie
            "Klaestron",
            "Korma",        // An Indian sauce
            "Kravat",        // Interesting spelling of the French word for "tie"
            "Krios",
            "Laertes",        // A king in a Greek tragedy
            "Largo",
            "Lave",            // The starting system in Elite
            "Ligon",
            "Lowry",        // The name of the "hero" in Terry Gilliam's "Brazil"
            "Magrat",        // The second of the witches in Pratchett's Discworld
            "Malcoria",
            "Melina",
            "Mentar",        // The Psilon home system in Master of Orion
            "Merik",
            "Mintaka",
            "Montor",        // A city in Ultima III and Ultima VII part 2
            "Mordan",
            "Myrthe",        // The name of my daughter
            "Nelvana",
            "Nix",            // An interesting spelling of a word meaning "nothing" in Dutch
            "Nyle",            // An interesting spelling of the great river
            "Odet",
            "Og",            // The last of the witches in Pratchett's Discworld
            "Omega",        // The end of it all
            "Omphalos",        // Greek for navel
            "Orias",
            "Othello",        // From Shakespeare
            "Parade",        // This word means the same in Dutch and in English
            "Penthara",
            "Picard",        // The enigmatic captain from ST:TNG
            "Pollux",        // Brother of Castor
            "Quator",
            "Rakhar",
            "Ran",            // A film by Akira Kurosawa
            "Regulas",
            "Relva",
            "Rhymus",
            "Rochani",
            "Rubicum",        // The river Ceasar crossed to get into Rome
            "Rutia",
            "Sarpeidon",
            "Sefalla",
            "Seltrice",
            "Sigma",
            "Sol",            // That's our own solar system
            "Somari",
            "Stakoron",
            "Styris",
            "Talani",
            "Tamus",
            "Tantalos",        // A king from a Greek tragedy
            "Tanuga",
            "Tarchannen",
            "Terosa",
            "Thera",        // A seldom encountered Dutch girl's name
            "Titan",        // The largest moon of Jupiter
            "Torin",        // A hero from Master of Magic
            "Triacus",
            "Turkana",
            "Tyrus",
            "Umberlee",        // A god from AD&D, which has a prominent role in Baldur's Gate
            "Utopia",        // The ultimate goal
            "Vadera",
            "Vagra",
            "Vandor",
            "Ventax",
            "Xenon",
            "Xerxes",        // A Greek hero
            "Yew",            // A city which is in almost all of the Ultima games
            "Yojimbo",        // A film by Akira Kurosawa
            "Zalkon",
            "Zuul"            // From the first Ghostbusters movie
    ));
    private final int SYSTEM_NAME_COUNT = SolarSystemName.size();
    private final double[] radius = {(.20*UNIVERSE_HEIGHT), (.26*UNIVERSE_HEIGHT), (.27*UNIVERSE_HEIGHT), (.27*UNIVERSE_HEIGHT)};
    private final int[] numInEachRad = {4, 4, 4, 4};

    public Universe() {
        solarSystems = new ArrayList<SolarSystem>();
        //add the capital system
        solarSystems.add(new SolarSystem(SolarSystemName.get(numberRandomizer(SYSTEM_NAME_COUNT - 1)),
                UNIVERSE_CENTER,
                7,
                Resources.MINERALPOOR));
        systemCount++;
        generateSystemLocation();

    }

    private void generateSystemLocation() {
        initArrays();
        Random randomizer = new Random();
        int mineralCounter = 0;
        for(int j = 0; j < numInEachRad.length; j++) {
            for (int i = 0; i < numInEachRad[j]; i++) {

                Resources resources = null;
                if (mineralCounter % 4 == 0) {
                    if (i > 3) {
                        resources = Resources.MINERALRICH;
                    } else {
                        resources = Resources.MINERALPOOR;
                    }
                } else {
                    resources = Resources.values()[randomizer.nextInt(13)];
                }
                int xVal = (int) (UNIVERSE_CENTER.getX() + ((radius[j]/2) * Math.cos(randomizer.nextInt(361))));
                int yVal = (int) (UNIVERSE_CENTER.getY() + ((radius[j]/2) * Math.sin(randomizer.nextInt(361))));
                int techLevelRand = ((8 -((j+1)*2)) + randomizer.nextInt(2));
                techLevelRand = techLevelRand > 0 ? techLevelRand : 1;
                solarSystems.add(new SolarSystem(SolarSystemName.get(numberRandomizer(SYSTEM_NAME_COUNT - 1)),
                        new Point(xVal, yVal),
                        techLevelRand,
                        resources));
                mineralCounter++;
            }
        }
    }

    private void initArrays()
    {
        for(int i = 0; i < radius.length; i++)
        {
            int indexMinus = i - 1;

            if(indexMinus != -1)
            {
                radius[i] = radius[i] + radius[indexMinus];
            }
        }
    }

    private int numberRandomizer(int upperRestraint){
        Random random = new Random();
        return random.nextInt(upperRestraint + 1);
    }

    public String toString() {
        StringBuffer buff = new StringBuffer();
        buff.append("Universe containing ").append(Integer.toString(SYSTEM_MAX)).append(
                " systems. System detail below:\n");
        for (SolarSystem system : solarSystems) {
            buff.append(system.toString()).append("\n");
        }
        return buff.toString();
    }

    public ArrayList<SolarSystem> getSystem()
    {
        return solarSystems;
    }
}
