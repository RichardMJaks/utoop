package Kodutööd.Kodutöö3;

import Custom.OOPFunctions;

/**
 * Attacks the target with a weapon, dealing 1d2 damage
 * On crit it deals 1d8 damage
 */
public class WeaponAttack implements Effect {
    private final String damageDie = "1d2";
    private final String critDie = "1d8";
    private final int actionPoints = 1;

    @Override
    public void onHit(Dude effectTarget, boolean isCritical) {
        int damage = OOPFunctions.rollDice(damageDie);
        if (isCritical) damage = OOPFunctions.rollDice(critDie);
        System.out.printf("Weapon Attack dealt %s damage to %s%n", damage, effectTarget);
        effectTarget.damageHealth(damage);
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
        return "Weapon Attack";
    }
}
