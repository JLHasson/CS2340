package main.model;

/**
 * Skilled Individual
 *
 * Each object that can have skills (Pirates, Mercenaries, Player)
 * must exten this interface
 */
public interface SkilledIndividual {
    public int getPilotingSkill();

    public int getFightingSkill();

    public int getTradingSkill();

    public int getEngineeringSkill();

    public int getInvestingSkill();
}
