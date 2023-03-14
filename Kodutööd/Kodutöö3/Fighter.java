package Kodutööd.Kodutöö3;

import java.util.ArrayList;

/**
 * Fighter is a tanky but inaccurate Dude with next starts:
 * Accuracy: 2
 * Armor: 17
 * HP: 20
 * Action Points: 2
 * AP Recovery: 2
 * Dexterity: 3
 * Fighter can use the next abilities:
 * - Weapon Attack (2 AP)
 * - Knockdown (2 AP)
 * - Enrage (0 AP)
 */
public class Fighter extends Dude {
    private boolean hasKnockedDown = false;

    // Constructor
    public Fighter() {
        super(2, 17, 20, 2, 2, 4, 3);
    }

    @Override
    Effect chooseEffect() {
        if (this.getHealth() < 10) return new Enrage();
        if (this.getActionPoints() > 1 && !this.hasKnockedDown) {
            this.hasKnockedDown = true;
            return new Knockdown();
        }
        this.hasKnockedDown = false;
        return new WeaponAttack();
    }

    @Override
    public String toString() {
        return "The Fighter";
    }

    @Override
    public String weapon() {
        return "Poleaxe";
    }
}
