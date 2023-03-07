package Kodutööd.Kodutöö3;

import Custom.OOPFunctions;

/**
 * This attack deals 1d10 points of damage at the end of the opponents turn.
 * Critical hit deal +5 damage
 */
public class Firebolt implements Effect {
    private final String damageDie = "1d10";
    private int critDamage;
    private final int actionPoints = 1;

    @Override
    public void onHit(Dude effectTarget, boolean isCritical) {
        critDamage = 0;
        effectTarget.addEffect(this);
        if (isCritical) critDamage += 5;
        System.out.println("Firebolt has hit " + effectTarget);
    }

    @Override
    public void onTurnStart(Dude effectTarget) {

    }

    @Override
    public void onTurnEnd(Dude effectTarget) {
        int damage = critDamage + OOPFunctions.rollDice(damageDie);
        effectTarget.damageHealth(damage);
        System.out.printf("Firebolt dealt %s damage to %s%n", damage, effectTarget);
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
        return "Firebolt";
    }
}
