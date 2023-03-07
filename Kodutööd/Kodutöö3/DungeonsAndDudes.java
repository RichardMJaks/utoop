package Kodutööd.Kodutöö3;

import Custom.OOPFunctions;

import java.text.MessageFormat;

/**
 * This is PEGI 7 eligible Dungeons & Dudes game simulator.
 */
public class DungeonsAndDudes {
    public static void main(String[] args) {
        Dude dueler1 = new Fighter();
        Dude dueler2 = new Wizard();


        Dude[] attackOrder;

        System.out.printf("The %s and the %s got into an argument about who has the better fighting stick.%n", dueler1, dueler2);
        System.out.printf("After they compared their %s and %s and found out that neither knew how to judge the other's stick, to prove once and for all that one has the better stick, they decided to fight to death.%n", dueler1.weapon(), dueler2.weapon());

        int dueler1Roll = OOPFunctions.d20();
        int dueler2Roll = OOPFunctions.d20();
        int dueler1Total = dueler1Roll + dueler1.getDexterity();
        int dueler2Total = dueler2Roll + dueler2.getDexterity();

        System.out.println("----------ROLL INITIATIVE----------");
        System.out.printf("%s rolled %s, for a total of %s%n", dueler1, dueler1Roll, dueler1Total);
        System.out.printf("%s rolled %s, for a total of %s%n", dueler2, dueler2Roll, dueler2Total);

        // Determine attack order
        if (dueler1Total > dueler2Total) attackOrder = new Dude[]{dueler1, dueler2};
        else attackOrder = new Dude[]{dueler2, dueler1};

        System.out.printf("%s got the first attack.%n", attackOrder[0]);

        boolean i = true; // Just a way to swap between them two
        while (dueler1.isAlive() && dueler2.isAlive()) {
            Dude currentAttacker = attackOrder[i ? 0 : 1];
            Dude currentTarget = attackOrder[i ? 1 : 0];

            System.out.printf("----------%s's turn----------%n", currentAttacker);
            currentAttacker.takeTurn(currentTarget);
            i = !i;
        }

        System.out.println("----------FIGHT END----------");

        // Check for different possibilities of what happened.
        int endCase = (dueler1.isAlive() ? 1 : 0) + (dueler2.isAlive() ? 2 : 0);

        switch (endCase) {
            case 0 ->
                    System.out.printf("%s and %s killed each other, proving once and for all, that both have equally good sticks.%n", dueler2, dueler1);
            case 1 ->
                    System.out.printf("%s managed to kill %s, proving once and for all, that their %s is superior.%n", dueler1, dueler2, dueler1.weapon());
            case 2 ->
                    System.out.printf("%s managed to kill %s, proving once and for all, that their %s is superior.%n", dueler2, dueler1, dueler2.weapon());
            case 3 -> {
                MessageFormat fmt = new MessageFormat("As {0} and {1} were both fighting, the Bard came out from behind the corner, " +
                        "flashing his wooden stick, which was clearly longer than the {0}''s {2} or {1}''s {3}. \nThus they both " +
                        "hid in the corner, ashamed.");
                System.out.println(fmt.format(new String[]{dueler1.toString(), dueler2.toString(), dueler1.weapon(), dueler2.weapon()}));
                System.out.println("Also, did you modify the program or did the author of this program screw something up? This should not happen!");
            }
        }
    }
}
