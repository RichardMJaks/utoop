package Praktikumid.Praktikum3;
import Custom.OOPFunctions;
import java.io.FileNotFoundException;

public class TestPank {
    public static void main(String[] args) throws FileNotFoundException {

        Klient k1 = new Klient(Integer.toString(OOPFunctions.randInt()), 300, 20_000);
        Klient k2 = new Klient(Integer.toString(OOPFunctions.randInt()), 500, 35_000);

        Kuldklient kk1 = new Kuldklient(Integer.toString(OOPFunctions.randInt()), 1000, 50_000, OOPFunctions.name());
        Kuldklient kk2 = new Kuldklient(Integer.toString(OOPFunctions.randInt()), 5000, 120_000, OOPFunctions.name());

        k1.teostaÜlekanne("Jaanus Sarmikas", 20);
        k2.teostaÜlekanne("Vabrant Kaktus", 100);
        kk1.teostaÜlekanne("Mardikas Karikakar", 500);
        kk1.teostaÜlekanne("Allu Kallis", 423);

        System.out.println("k1: " + k1.arvutaTehinguTasud() + " " + k1.arvutaPortfelliHaldustasu());
        System.out.println("k2: " + k2.arvutaTehinguTasud() + " " + k2.arvutaPortfelliHaldustasu());
        System.out.println("kk1: " + kk1.arvutaTehinguTasud() + " " + kk1.arvutaPortfelliHaldustasu() + " " + kk1.getKliendiHaldur());
        System.out.println("kk2: " + kk2.arvutaTehinguTasud() + " " + kk2.arvutaPortfelliHaldustasu() + " " + kk2.getKliendiHaldur());
        System.out.println(kk1);
        System.out.println(kk2);
    }
}
