package Kodutööd.Kodutöö3;

import Custom.OOPFunctions;

/**
 * Casts a spiderweb on the opponent, reducing their action points by 1 for the next 1d4 turns
 * On crit reduce action points by 2 for the next 2d4 turns
 */
public class Spiderweb implements Effect {
    private final String durationDie = "1d4";
    private int remainingDuration;
    private final int actionPoints = 2;
    private int reduceAP = 1;

    @Override
    public void onHit(Dude effectTarget, boolean isCritical) {
        effectTarget.addEffect(this);
        this.remainingDuration = OOPFunctions.rollDice(durationDie);
        if (isCritical) {
            this.remainingDuration += OOPFunctions.rollDice(durationDie);
            this.reduceAP *= 2;
        }
        System.out.println(effectTarget + " has been afflicted by a spider web for %s turns".formatted(remainingDuration));
    }

    @Override
    public void onTurnStart(Dude effectTarget) {
        effectTarget.reduceActionPoints(this.reduceAP);
        --remainingDuration;
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
        if (remainingDuration <= 0) System.out.println("Spiderweb has worn off");
        return remainingDuration <= 0;
    }

    public String toString() {
        return "Spiderweb";
    }
}
