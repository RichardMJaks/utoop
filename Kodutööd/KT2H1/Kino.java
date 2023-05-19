package Kodutööd.KT2H1;

import javax.xml.crypto.Data;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

@SuppressWarnings("NonAsciiCharacters")
public class Kino {
    static Saal[] saalid = new Saal[] {
            new Saal(1, 150),
            new Saal(2, 68),
            new Saal(3, 10)
    };

    static Map<Integer, Seanss> seansid;

    public static void main(String[] args) throws Exception {
        seansid = loeSeansid("src/Kodutööd/KT2H1/seansid.txt");
        try (Scanner konsool = new Scanner(System.in, StandardCharsets.UTF_8)) {
            while (true) {
                System.out.printf("Tere tulemast kinno!%nValige tegevus:" +
                        "%nOsta (O)" +
                        "%nSeansi info (V)" +
                        "%nSalvesta (S)" +
                        "%n: ");

                // Loe sisendit ja tegutse
                switch (konsool.next().toUpperCase()) {
                    case "O" -> osta(konsool);
                    case "V" -> info(konsool);
                    case "S" -> salvesta();
                }
            }
        }

    }

    private static void osta(Scanner konsool) throws Exception {
        // Väljastame ekraanile info
        System.out.println("Millisele seansile soovite pileti osta?");
        seansid.forEach((k, v) -> System.out.printf("%d. " + v));

        // Küsime seansi numbrit
        System.out.print("Palun valige soovitud seanss numbri järgi: ");
        int seansiNr = konsool.nextInt();
        Seanss seanss = seansid.get(seansiNr);

        boolean lipp = true;
        while (lipp) {
            try {
                System.out.print("Palun valige soovitud koha number: ");

                int kohaNr = konsool.nextInt();

                seanss.müüPilet(kohaNr);
                System.out.println("Saite pileti edukalt ostetud! Head vaatamist!");

                lipp = false;
            } catch (VabadKohadPuuduvadErind e) {
                System.out.println("Vabandust, aga sellele seansile kohad puuduvad.");
                lipp = false;
            } catch (KohtHõivatudErind e) {
                System.out.println("Vabandust, aga see koht on võetud");
            }
        }
    }

    private static void info(Scanner konsool) throws Exception {
        // Väljastame ekraanile info
        System.out.println("Millisele seansile soovite pileti osta?");
        seansid.forEach((k, v) -> System.out.printf("%d. " + v));

        // Küsime seansi numbrit
        System.out.print("Palun valige soovitud seanss numbri järgi: ");
        int seansiNr = konsool.nextInt();
        Seanss seanss = seansid.get(seansiNr);
        System.out.println("Nimi: " + seanss.getFilm() +
                "\nSaal: " + seanss.getSaal().getNumber() +
                "\nAlgusaeg: " + seanss.getAlgusaeg().toString() +
                "\nVabu kohti: " + seanss.vabuKohti());
    }

    private static void salvesta() throws IOException {
        StringBuilder info = new StringBuilder();
        try (DataOutputStream dos =
                     new DataOutputStream(new FileOutputStream("seansid.dat")))
        {
            Seanss[] seansidMassiiv = seansid.values().toArray(Seanss[]::new);
            dos.writeInt(seansidMassiiv.length);
            for (Seanss seanss : seansidMassiiv) {
                dos.writeUTF(seanss.getFilm());
                dos.writeUTF(seanss.getAlgusaeg().toString());
                dos.writeInt(seanss.getSaal().getNumber());
                Set<Pilet> piletid = seanss.getMüüdud();
                dos.writeInt(piletid.size());
                for (Pilet pilet : piletid) {
                    dos.writeInt(pilet.getKoht());
                }
            }
        }
    }

    private static Map<Integer, Seanss> loeSeansid (String fail) throws Exception {
        List<String> read = Files.readAllLines(Path.of(fail));
        Map<Integer, Seanss> seansid = new HashMap<>();
        for (int i = 1; i <= read.size(); i++) {
            // Teeme sisendi infokildudeks
            String rida = read.get(i - 1);
            String[] info = rida.split(";");

            // Loeme andmed sisse
            String filmiNimi = info[0];
            LocalDateTime kellaaeg = LocalDateTime.parse(info[1]);
            int saaliNr = Integer.parseInt(info[2]);
            int[] kohad = Arrays.stream(info[3].split(","))
                    .mapToInt(Integer::parseInt)
                    .limit(10)
                    .toArray();

            // Loome seansi
            Seanss seanss = new Seanss(filmiNimi, kellaaeg, saalid[saaliNr - 1]);

            // Lisame seansile piletid
            for (int koht : kohad) seanss.müüPilet(koht);

            // Paneme seansi sõnastikku
            seansid.put(i, seanss);
        }

        return seansid;
    }
}
