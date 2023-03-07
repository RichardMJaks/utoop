package Kodutööd.Kodutöö3;

public interface Effect {
    void onHit(Dude effectTarget, boolean isCritical);

    void onTurnStart(Dude effectTarget);

    void onTurnEnd(Dude effectTarget);

    int requiredActionPoints();

    boolean isExpired();

    String toString();
}
