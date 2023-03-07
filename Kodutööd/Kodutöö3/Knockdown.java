package Kodutööd.Kodutöö3;

/**
 * Knocks the opponent down, reducing their action points to zero
 * On crit reduces action points to zero at the start of their turn too
 */
public class Knockdown implements Effect {
    private final int actionPoints = 4;

    @Override
    public void onHit(Dude effectTarget, boolean isCritical) {
        effectTarget.setActionPoints(0);
        if (isCritical) effectTarget.addEffect(this);
        System.out.println(effectTarget + " has been knocked down and lost all their action points%s".formatted(isCritical ? " and has a harder time recovering them" : ""));
    }

    @Override
    public void onTurnStart(Dude effectTarget) {
        effectTarget.setActionPoints(0);
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

    public String toString() {
        return "Knockdown";
    }
}
