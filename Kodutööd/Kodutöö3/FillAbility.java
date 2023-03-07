package Kodutööd.Kodutöö3;

/**
 * Fills ability points on the start of the next turn
 * On crit fills ability at the start of the next two turns
 */
public class FillAbility implements SelfEffect {
    private int duration = 1;
    private final int actionPoints = 0;

    @Override
    public void onHit(Dude effectTarget, boolean isCritical) {
        if (isCritical) this.duration *= 2;
        effectTarget.addEffect(this);
        System.out.printf("%s used Fill Ability%n", effectTarget);
    }

    @Override
    public void onTurnStart(Dude effectTarget) {
        effectTarget.setActionPoints(effectTarget.getMaxActionPoints());
        duration--;
        System.out.printf("%s filled their ability points%n", effectTarget);
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
        if (duration <= 0) System.out.println("Fill Ability has worn off");
        return duration <= 0;
    }

    @Override
    public String toString() {
        return "Fill Ability";
    }
}