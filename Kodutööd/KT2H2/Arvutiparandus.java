package Kodutööd.KT2H2;

import javax.xml.crypto.Data;
import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

@SuppressWarnings({"NonAsciiCharacters", "SpellCheckingInspection"})
public class Arvutiparandus {
    public static void main(String[] args) throws IOException {
        Scanner konsool = new Scanner(System.in);

        PriorityQueue<Arvuti> tööd = loeFailistArvutid();
        HashMap<String, Double> töötajad = loeTöötajad();
        List<Arvuti> tehtudTööd = new ArrayList<>();
        konsooliTsükkel:
        {
            while (true) {
                System.out.print("Kas soovid parandada (P), uut tööd registreerida (R) või lõpetada (L) ? ");
                switch (konsool.next()) {
                    case "R" -> tööd.add(looUusTöö(konsool));
                    case "P" -> paranda(konsool, tööd, töötajad, tehtudTööd);
                    case "L" -> {
                        lõpeta(tehtudTööd, tööd);
                        break konsooliTsükkel;
                    }
                }
            }
        }
    }

    private static void lõpeta(List<Arvuti> tehtudTööd, PriorityQueue<Arvuti> ootelTööd) throws IOException {
        int tehtudTöödeArv = tehtudTööd.size();
        int ootelTöödeArv = ootelTööd.size();

        double teenitudRaha = 0.0;

        HashMap<String, Integer> parandatudArvutid = new HashMap<>();

        try (DataOutputStream tehtudKirjutaja = new DataOutputStream(new FileOutputStream("tehtud.dat"));
             PrintWriter ootelKirjutaja = new PrintWriter("ootel.txt")) {
            tehtudKirjutaja.writeInt(tehtudTöödeArv);
            for (Arvuti arvuti : tehtudTööd) {
                // Töötleme tootjaga
                String tootja = arvuti.getTootja();
                if (parandatudArvutid.containsKey(tootja))
                    //noinspection DataFlowIssue
                    parandatudArvutid.compute(tootja, (k, v) -> v + 1);
                else
                    parandatudArvutid.put(tootja, 1);
                tehtudKirjutaja.writeUTF(tootja);                                    // Kirjutame tootja

                tehtudKirjutaja.writeUTF(arvuti.getRegistreerimiseAeg().toString()); // Kirjutame registreerimisaja

                double arveHind = arvuti.getArveHind();
                teenitudRaha += arveHind;                                            // Lisame arve hinna teenitud rahasse
                tehtudKirjutaja.writeDouble(arveHind);                               // Kirjutame arve hinna
            }
            for (Arvuti arvuti : ootelTööd) {
                ootelKirjutaja.write(arvuti.toString() + "\n");
            }

            // Väljasta kokkuvõte
            System.out.println("Sessiooni kokkuvõte:");
            System.out.printf("Teenitud raha: %.2f€%n", teenitudRaha);
            System.out.println("Parandatud arvuteid:");
            parandatudArvutid.forEach((k, v) -> System.out.printf("%s: %d%n", k, v));
            System.out.printf("Ootele jäi %s arvuti(t).%n", ootelTöödeArv);
        }
    }

    private static HashMap<String, Double> loeTöötajad() throws IOException {
        HashMap<String, Double> töötajad = new HashMap<>();
        try (DataInputStream lugeja = new DataInputStream(new FileInputStream("tunnitasud.dat"))) {
            int arv = lugeja.readInt();
            for (int i = 0; i < arv; i++) {
                String nimi = lugeja.readUTF();
                Double tasu = lugeja.readDouble();
                töötajad.put(nimi, tasu);
            }
        }
        return töötajad;
    }

    private static void paranda(Scanner konsool, PriorityQueue<Arvuti> tööd, HashMap<String, Double> töötajad, List<Arvuti> tehtudTööd) {
        if (tööd.size() == 0) System.out.println("Ootel pole ühtegi arvutit!");
        Arvuti arvuti = tööd.poll(); // Võtame arvuti millega tööd teha

        assert arvuti != null; // Peab olema arvuti, mida parandada, kui ei ole on midagi katastroofiliselt valesti läinud

        System.out.println(arvuti);

        // Loeme minutid
        while (true) {
            try {
                System.out.print("Sisesta parandamiseks kulunud aeg (täisminutites): ");
                int minutid = konsool.nextInt();

                // Loeme nime
                System.out.print("Sisesta enda nimi: ");
                String nimi = konsool.next();

                double tasu = töötajad.get(nimi);

                double baashind = tasu * (minutid / 60.0);

                double arve = arvuti.arvutaArveSumma(baashind);// Arvutame arve summa

                tehtudTööd.add(arvuti);
                System.out.printf("Töö tehtud, arve summa on %.2f €!%n", arve);
                break;
            } catch (InputMismatchException e) {
                System.out.println("Sisend on ebakorrektne, palun sisestage uuesti.");
            } catch (NullPointerException e) {
                System.out.println("Sellise nimega töötajat pole olemas, palun sisestage uuesti.");
            }
        }
    }

    private static Arvuti looUusTöö(Scanner konsool) {
        while (true) {
            System.out.print("Sisesta uus töö (registreerimisaega ei tohi olla) ");
            String arg = konsool.next();
            try {
                Arvuti arvuti = loeArvuti(arg);
                return arvuti;
            } catch (FormaadiErind e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }

    private static Arvuti loeArvuti(String arg) throws FormaadiErind {
        String[] jaotus = arg.split("@");

        String[] töö = jaotus[0].split(";");

        // Erindi käsitlemine
        if (töö.length < 2 || töö.length > 3)
            throw new FormaadiErind("Rida " + arg + "; Töö välju on liiga " + (töö.length < 2 ? "vähe." : "palju."));
        if (töö.length == 3 && !töö[2].equals("monitoriga"))
            throw new FormaadiErind("Rida " + arg + "; Töö kolmas väli peab omama väärtust \"monitoriga\".");
        if (!töö[1].equals("tavatöö") && !töö[1].equals("kiirtöö"))
            throw new FormaadiErind("Rida " + arg + "; Töö tüüp peab olema \"tavatöö\" või \"kiirtöö\".");

        // Määrame registreerimisaja, algväärtus praegune
        LocalDateTime registreerimisAeg = LocalDateTime.now();
        if (jaotus.length > 1) // Kui registreerimisaeg on olemas
            try {
                registreerimisAeg = LocalDateTime.parse(jaotus[1]);
            } catch (Exception e) {
                throw new FormaadiErind("Rida " + arg + "; Registreerimisaeg pole korrektses formaadis.");
            }

        // Algväärtustame tagastatava arvuti
        Arvuti arvuti = new Arvuti(
                töö[0],
                registreerimisAeg,
                töö[1].equals("kiirtöö")
        );
        if (Arrays.asList(töö).contains("monitoriga")) // Kui on monitoriga arvuti
            arvuti = new VäliseMonitorigaArvuti(
                    töö[0],
                    registreerimisAeg,
                    töö[1].equals("kiirtöö")
            );

        return arvuti;
    }

    private static PriorityQueue<Arvuti> loeFailistArvutid() throws IOException {
        PriorityQueue<Arvuti> arvutid = new PriorityQueue<>(new ArvutiVõrdleja());
        try (Scanner lugeja = new Scanner(Path.of("ootel_arvutid.txt"))) {
            while (lugeja.hasNextLine())
                try {
                    arvutid.add(loeArvuti(lugeja.nextLine()));
                } catch (FormaadiErind e) {
                    System.out.println(e.getLocalizedMessage());
                }
        }

        return arvutid;
    }
}
