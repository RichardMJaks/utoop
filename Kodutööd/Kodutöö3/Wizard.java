package Kodutööd.Kodutöö3;

import java.util.ArrayList;

/**
 * Wizard is an accurate but feeble Dude with next starts:
 * Accuracy: 5
 * Armor: 15
 * HP: 10
 * Action Points: 3
 * AP Recovery: 1
 * Dexterity: 5
 * Wizard can use the next abilities:
 * - Firebolt (1 AP)
 * - Spiderweb (2 AP)
 * - Fill Ability (0 AP)
 */
public class Wizard extends Dude {
    private int deltaWebAttack = 3;

    public Wizard() {
        this.accuracy = 5;
        this.armor = 15;
        this.health = 10;
        this.actionPointRecovery = 1;
        this.actionPoints = 3;
        this.maxAP = 3;
        this.dexterity = 5; // Added for choosing attack order
        this.afflictedEffects = new ArrayList<>();
    }

    @Override
    Effect chooseEffect() {
        if (this.deltaWebAttack > 2 && this.actionPoints > 1) {
            this.deltaWebAttack = 0;
            return new Spiderweb();
        } // If previous web attack has expired and can use it again
        this.deltaWebAttack++;
        if (this.actionPoints > 0) return new Firebolt();
        return new FillAbility();
    }

    public String toString() {
        return "The Wizard";
    }

    public String weapon() {
        return "Staff";
    }
}
