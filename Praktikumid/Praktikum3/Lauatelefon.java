package Praktikumid.Praktikum3;

public class Lauatelefon extends Telefon {
    private String omanik;
    public Lauatelefon(String nr, String helin, String omanik) {
        super(nr, helin);
        this.omanik = omanik;
    }

    @Override
    String tähtisInfo() {
        return null;
    }
}
