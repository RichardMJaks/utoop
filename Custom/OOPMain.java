package Custom;


import java.io.IOException;

/**
 * This class is to be used for methods and functions which do not make sense to be used in a homework file
 */
public class OOPMain {
    public static void main(String[] args) throws IOException {
        for (int i = 30; i < 100; i++)
            OOPFunctions.GenerateRandomNumberFiles(1_000_000_000,
                    100_000,
                    "/home/dotcom/IdeaProjects/OOP/src/Kodutööd/Kodutöö4/Paralleel/arvud" + i,
                    (i % 2 == 1) ? " " : "\n");
    }
}
