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
        this.accuracy = 2;
        this.armor = 17;
        this.health = 20;
        this.actionPointRecovery = 2;
        this.actionPoints = 2;
        this.maxAP = 4;

        this.dexterity = 3; // Added for choosing attack order

        this.afflictedEffects = new ArrayList<>();
    }

    @Override
    Effect chooseEffect() {
        if (this.health < 10) return new Enrage();
        if (this.actionPoints > 1 && !this.hasKnockedDown) {
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
