package Kodutööd.KT2H2;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

@SuppressWarnings({"NonAsciiCharacters", "SpellCheckingInspection"})
public class Arvuti {
    private String tootja;
    private LocalDateTime registreerimiseAeg;
    private boolean kiirtöö;

    public double getArveHind() {
        return arveHind;
    }

    public void setArveHind(double arveHind) {
        this.arveHind = arveHind;
    }

    private double arveHind;

    public Arvuti(String tootja, LocalDateTime registreerimiseAeg, boolean kiirtöö) {
        this.tootja = tootja;
        this.registreerimiseAeg = registreerimiseAeg;
        this.kiirtöö = kiirtöö;
    }

    public double arvutaArveSumma(double baasHind) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        this.arveHind = Double.parseDouble( // Teeme tagasi double-tüübiks
                decimalFormat.format( // Ümardame
                        baasHind + 2.0 + (kiirtöö ? 10.0 : 0.0)
                )
        );
        return this.arveHind;
    }
    public boolean onKiirtöö() {
        return kiirtöö;
    }

    public String getTootja() {
        return tootja;
    }

    public LocalDateTime getRegistreerimiseAeg() {
        return registreerimiseAeg;
    }

    @Override
    public String toString() {
        return tootja + ";" + (kiirtöö ? "kiirtöö" : "tavatöö") + "@" + registreerimiseAeg.toString();
    }
}
