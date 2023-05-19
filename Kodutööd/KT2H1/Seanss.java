package Kodutööd.KT2H1;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@SuppressWarnings("NonAsciiCharacters")
public class Seanss {
    String film;
    LocalDateTime algusaeg;
    Saal saal;
    Set<Pilet> müüdud;

    public void müüPilet(int kohaNr) throws Exception {
        int kohtadearv = saal.getKohtadeArv();

        // Kontrollime koha vabadust
        if (vabuKohti() <= 0) throw new VabadKohadPuuduvadErind();
        if (müüdud.stream().map(Pilet::getKoht).anyMatch(g -> g == kohaNr)) throw new KohtHõivatudErind();

        // Genereerime tehinguID
        int tehinguID = genereeriTehinguID();

        Pilet pilet = new Pilet(kohaNr, tehinguID);
        müüdud.add(pilet);
    }

    public int vabuKohti() {
        return saal.getKohtadeArv() - müüdud.size();
    }

    public int genereeriTehinguID() {
        return (int) ((algusaeg.getDayOfYear() + algusaeg.getHour() + algusaeg.getMinute()) / (int) (Math.random() * 10) / System.currentTimeMillis());
    }

    public String getFilm() {
        return film;
    }

    public LocalDateTime getAlgusaeg() {
        return algusaeg;
    }

    public Saal getSaal() {
        return saal;
    }

    public Set<Pilet> getMüüdud() {
        return müüdud;
    }

    public Seanss(String film, LocalDateTime algusaeg, Saal saal) {
        this.film = film;
        this.algusaeg = algusaeg;
        this.saal = saal;
        this.müüdud = new HashSet<>();
    }

    public String toString() {
        return "Nimi: " + film;
    }
}
