package Praktikumid.Praktikum3;

import java.util.ArrayList;
import java.util.List;

public class Klient {

    private String isikukood;
    private double saldo;
    private List<String> tehingud = new ArrayList<>();
    private double aktsiaPortfelliVäärtus;

    public Klient(String isikukood, double saldo, double aktsiaPortfelliVäärtus) {
        this.isikukood = isikukood;
        this.saldo = saldo;
        this.aktsiaPortfelliVäärtus = aktsiaPortfelliVäärtus;
    }

    public void teostaÜlekanne(String kontole, double summa) {
        tehingud.add("ülekanne kontole " + kontole + ", summa " + summa);
        saldo -= summa;
    }

    public double arvutaTehinguTasud() {
        return 0.1 * tehingud.size();
    }

    public double arvutaPortfelliHaldustasu() {
        return aktsiaPortfelliVäärtus * 0.005;
    }

    public String toString() {
        return "klient " + isikukood + ", saldo " + saldo;
    }
    public double getAktsiaPortfelliVäärtus() {
        return this.aktsiaPortfelliVäärtus;
    }
}
