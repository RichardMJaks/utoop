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
        super(5, 15, 10, 3, 1, 3, 5);
    }

    @Override
    Effect chooseEffect() {
        if (this.deltaWebAttack > 2 && this.getActionPoints() > 1) {
            this.deltaWebAttack = 0;
            return new Spiderweb();
        } // If previous web attack has expired and can use it again
        this.deltaWebAttack++;
        if (this.getActionPoints() > 0) return new Firebolt();
        return new FillAbility();
    }

    public String toString() {
        return "The Wizard";
    }

    public String weapon() {
        return "Staff";
    }
}
