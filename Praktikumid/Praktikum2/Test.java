package Praktikumid.Praktikum2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {

    // Suure algustähe loomine kuna kes iganes selle nimede faili kokku pani ei teinud oma tööd korralikult
    static String capitalize (String string) {
        return string.substring(0,1).toUpperCase() + string.substring(1);
    }
        public static void main(String[] args) throws Exception {


            List<String> nimed = new ArrayList<>();

            File file = new File("/src/nimed.txt");
            Scanner scanner = new Scanner(file, "UTF-8");

                while (scanner.hasNextLine()) {
                    nimed.add(scanner.nextLine());
                }
                scanner.close();

                String nimi = capitalize(
                        nimed.get(
                                (int) (Math.random() * nimed.size())
                        )
                );

            TasulineLõbustus ratas = new TasulineLõbustus(2.25, new Vaateratas());
            VanuseKontrollija lasketiir = new VanuseKontrollija(10, new TasulineLõbustus(1.5, new Lasketiir()));
            // Vastupidi tehes läheks kuludesse lasketiir kirja, kuigi võibolla sisse ei lastud
            Kloun klounKuri = new Kloun(nimi);
            LõbustavKloun kloun = new LõbustavKloun(klounKuri);

            List<Lõbustus> lõbustused = new ArrayList<>();
            lõbustused.add(ratas);
            lõbustused.add(lasketiir);
            lõbustused.add(kloun);

            Lõbustuspark park = new Lõbustuspark(lõbustused);
            Külastaja noor = new Külastaja(9);
            Külastaja mitteNiiNoor = new Külastaja(11);

            park.alustaSeiklust(noor);
            park.alustaSeiklust(mitteNiiNoor);
        }
}
class Külastaja {
    private List<String> külastuseKirjeldused;
    private int vanus;
    private double kulud = 0;

    public void lisaKulu(double kulu) {
        this.kulud += kulu;
    }

    public double kuludeSumma() {
        return kulud;
    }

    public int getVanus() {
        return this.vanus;
    }

    public void lisaKirjeldus(String kirjeldus) {
        this.külastuseKirjeldused.add(kirjeldus);
    }

    public List<String> getKülastuseKirjeldused() {
        return külastuseKirjeldused;
    }

    public Külastaja(int vanus) {
        this.külastuseKirjeldused = new ArrayList<String>();
        this.vanus = vanus;
    }
}
interface Lõbustus {
    void lõbusta(Külastaja külastaja);
}


class TasulineLõbustus implements Lõbustus {
    private double hind;
    private Lõbustus delegaat;
    public void lõbusta(Külastaja külastaja) {
        delegaat.lõbusta(külastaja);
        külastaja.lisaKulu(hind);
        külastaja.lisaKirjeldus("tasusin külastuse eest " + hind);
    }
    public TasulineLõbustus(double hind, Lõbustus delegaat) {
        this.hind = hind;
        this.delegaat = delegaat;
    }
}
class VanuseKontrollija implements Lõbustus {

    private int nõutudVanus;
    private Lõbustus lõbustus;
    public void lõbusta (Külastaja külastaja) {
        if (külastaja.getVanus() < nõutudVanus) {
            System.out.println("külastaja ei läbinud vanusekontrolli");
            return;
        }
        lõbustus.lõbusta(külastaja);
    }

    public VanuseKontrollija(int nõutudVanus, Lõbustus delegaat) {
        this.nõutudVanus = nõutudVanus;
        this.lõbustus = delegaat;
    }
}


class Vaateratas implements Lõbustus {
    public void lõbusta(Külastaja külastaja) {
        külastaja.lisaKirjeldus("Külastasin vaateratast");
    }
}

class Lasketiir implements Lõbustus {
    public void lõbusta(Külastaja külastaja) {
        külastaja.lisaKirjeldus(String.format("Tabasin lasketiirus %s sihtmärki",
                (int) (Math.random() * 21)
        ));
    }
}

class Kloun {
    private String nimi;
    public Kloun(String nimi) {
        this.nimi = nimi;
    }
    public void esine(Külastaja külastaja) {
        külastaja.lisaKirjeldus("nägin klouni nimega " + nimi);
    }
}

class LõbustavKloun implements Lõbustus{
    private Kloun kloun;
    public LõbustavKloun(Kloun kloun) {
        this.kloun = kloun;
    }

    public void lõbusta(Külastaja külastaja) {
        kloun.esine(külastaja);
    }
}


class Lõbustuspark {
    private List<Lõbustus> lõbustused;

    public void alustaSeiklust(Külastaja külastaja) {
        System.out.println("alustan seiklust");

        for (Lõbustus lõbustus : lõbustused) {lõbustus.lõbusta(külastaja);}

        List<String> kirjed = külastaja.getKülastuseKirjeldused();
        for (String el : kirjed) {
            System.out.println(el);
        }
        System.out.println(külastaja.kuludeSumma());
    }
    public Lõbustuspark(List<Lõbustus> lõbustused) {
        this.lõbustused = lõbustused;
    }
}