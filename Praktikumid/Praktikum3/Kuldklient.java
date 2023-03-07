package Praktikumid.Praktikum3;

public class Kuldklient extends Klient {
    private String kliendiHaldur;

    public Kuldklient(String isikukood, double saldo, double aktsiaPortfelliVäärtus, String kliendiHaldur) {
        super(isikukood, saldo,aktsiaPortfelliVäärtus); // Klient konstruktori käivitamine
        this.kliendiHaldur = kliendiHaldur;
    }

    @Override
    public double arvutaPortfelliHaldustasu() {
        double portfell = getAktsiaPortfelliVäärtus();
        if (portfell <= 50_000) return 0;
        else return (portfell - 50_000) * 0.005;

    }

    public String toString() {
        return super.toString() + ", kliendihaldur " + this.kliendiHaldur;
    }
    public String getKliendiHaldur() {
        return kliendiHaldur;
    }
    public void setKliendiHaldur(String kliendiHaldur) {
        this.kliendiHaldur = kliendiHaldur;
    }
}
