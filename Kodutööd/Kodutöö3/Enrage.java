package Kodutööd.Kodutöö3;

import Custom.OOPFunctions;

/**
 * Enrages, healing 1d2 health and gaining 1d2 armor
 * On crit gets 2d2 armor
 */
public class Enrage implements SelfEffect {
    private final String healingDie = "1d2";
    private final String armorDie = "1d2";
    private final int actionPoints = 0;

    @Override
    public void onHit(Dude effectTarget, boolean isCritical) {
        int healing = OOPFunctions.rollDice(healingDie);
        int armorIncrease = OOPFunctions.rollDice(armorDie) + (isCritical ? OOPFunctions.rollDice(armorDie) : 0);
        effectTarget.healHealth(healing);
        effectTarget.setArmor(effectTarget.getArmor() + armorIncrease);
        System.out.println(effectTarget + " enrages, healing %s health and gaining %s armor".formatted(healing, armorIncrease));
    }

    @Override
    public void onTurnStart(Dude effectTarget) {
    }

    @Override
    public void onTurnEnd(Dude effectTarget) {

    }

    @Override
    public int requiredActionPoints() {
        return this.actionPoints;
    }

    @Override
    public boolean isExpired() {
        return true;
    }

    @Override
    public String toString() {
        return "Enrage";
    }
}
