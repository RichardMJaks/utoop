package Custom;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import java.io.FileNotFoundException;

public class OOPFunctions {

    /**
     * Capitalize the first letter in string
     * @param string String to capitalize
     * @return Capitalized string
     */
    static private String capitalize (String string) throws RuntimeException    {
        if (string.length() <= 1) throw new RuntimeException("String must have a length of at least 2");
        return string.substring(0,1).toUpperCase() + string.substring(1);
    }

    /**
     * Generates a random name
     * @return Name
     * @throws FileNotFoundException - When can't find the names file
     */
    static public String name() throws FileNotFoundException {
        List<String> nimed = new ArrayList<>();
        File file = new File("src/Custom/nimed.txt");
        Scanner scanner = new Scanner(file, "UTF-8");

        while (scanner.hasNextLine()) {
            nimed.add(scanner.nextLine());
        }
        scanner.close();

        return capitalize(nimed.get((int) (Math.random() * nimed.size())));
    }

    /**
     * Generates a random number between 1000 inclusive and 2000 exclusive
     * @return int
     */
    static public int randInt1k2k() {return (int) (1000 + Math.random() * 1000);}

    static public int randInt(int startInc, int endExc) {return (int) (startInc + Math.random() * (endExc - startInc));}

    static public void GenerateRandomNumberFiles(long randomRange, long amount, String fileName, String delimiter) throws IOException {
        File myFile = new File(fileName);
        myFile.createNewFile();
        FileWriter writer = new FileWriter(fileName);
        for (int i = 0; i < amount; i++)
            writer.append((long) (Math.random() * randomRange) + delimiter);
    }

    /**
     * Rolls a die
     * @param dice xdy, where x is count and y is dice size
     * @return a dice roll
     */
    static public int rollDice(String dice) {
        int[] i = Arrays.stream(dice.split("d"))
                .mapToInt(Integer::parseInt)
                .toArray();
        int sum = 0;
        for (int j = 0; j < i[0]; j++) {sum += (int) (1 + Math.random() * (i[1] + 1));}
        return sum;
    }
    //endregion
}
