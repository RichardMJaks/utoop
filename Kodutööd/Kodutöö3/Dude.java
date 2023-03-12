package Kodutööd.Kodutöö3;

import Custom.OOPFunctions;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Dude {
    protected int accuracy;
    protected int armor;
    protected int health;
    protected int actionPoints;
    protected int actionPointRecovery;
    protected int maxAP;
    protected int dexterity; // Added for choosing attack order
    protected List<Effect> afflictedEffects; // List of afflicted effects

    public void takeTurn(Dude attackTarget) {
        // Recover action points
        this.actionPoints += Math.min(this.actionPointRecovery, this.maxAP);

        // Turn start effects
        for (Effect effect : this.afflictedEffects) effect.onTurnStart(this);

        // Action phase
        Effect chosenEffect = this.chooseEffect();
        if (chosenEffect.requiredActionPoints() <= actionPoints) { // Enough action points to use the attack?
            boolean isSelf = chosenEffect instanceof SelfEffect;
            if (isSelf) attackTarget = this; // Reassign to self-cast in the case of SelfEffect

            System.out.printf("%s uses %s on %s%n", this, chosenEffect, (attackTarget == this) ? "themself" : attackTarget);

            // Roll
            int roll = OOPFunctions.rollDice("1d20");
            int rollTotal = roll + this.accuracy;
            System.out.printf("%s rolled %s, for a total of %s%n", this, roll, rollTotal);

            // Hit or no hit
            if (rollTotal > attackTarget.getArmor() || isSelf) {
                if (roll == 20) System.out.println("It is a critical!");
                chosenEffect.onHit(attackTarget, roll == 20);
            } else System.out.printf("%s missed the attack%n", this);
            this.actionPoints -= chosenEffect.requiredActionPoints();

        } else System.out.printf("%s can't do anything right now%n", this);

        // Turn end effects
        for (Effect effect : this.afflictedEffects) effect.onTurnEnd(this);

        // Clear out expired effects
        this.afflictedEffects = this.afflictedEffects.stream().filter(i -> !i.isExpired()).collect(Collectors.toList());
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    abstract Effect chooseEffect();

    public String toString() {
        return "The Dude";
    }

    public String weapon() {
        return "The Stick";
    }

    public void addEffect(Effect effect) {
        this.afflictedEffects.add(effect);
    }

    public int getHealth() {
        return this.health;
    }

    ///region Getters & Setters
    //region Health
    public void setHealth(int value) {
        this.health = value;
    }

    public void damageHealth(int damage) {
        this.health -= damage;
    }

    public void healHealth(int healing) {
        this.health += healing;
    }

    public int getActionPoints() {
        return this.actionPoints;
    }

    //endregion
    //region Action Points
    public void setActionPoints(int value) {
        this.actionPoints = value;
    }

    public void reduceActionPoints(int value) {
        this.actionPoints -= value;
    }

    public void restoreActionPoints(int value) {
        this.actionPoints += value;
    }

    public int getMaxActionPoints() {
        return maxAP;
    }

    public int getArmor() {
        return this.armor;
    }

    //endregion
    //region Armor
    public void setArmor(int value) {
        this.armor = value;
    }

    //endregion
    //region Dexterity
    public int getDexterity() {
        return dexterity;
    }
    //endregion
    //endregion

}
