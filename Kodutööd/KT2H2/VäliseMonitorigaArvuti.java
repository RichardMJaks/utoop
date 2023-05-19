package Kodutööd.KT2H2;

import java.time.LocalDateTime;
import java.util.Arrays;

@SuppressWarnings({"NonAsciiCharacters", "SpellCheckingInspection"})
public class VäliseMonitorigaArvuti extends Arvuti {
    public VäliseMonitorigaArvuti(String tootja, LocalDateTime registreerimiseAeg, boolean kiirtöö) {
        super(tootja, registreerimiseAeg, kiirtöö);
    }

    @Override
    public double arvutaArveSumma(double baasHind) {
        setArveHind(super.arvutaArveSumma(baasHind) + 1);
        return getArveHind();
    }

    @Override
    public String toString() {
        return getTootja() + ";" + (onKiirtöö() ? "kiirtöö" : "tavatöö") + ";monitoriga" + "@" + getRegistreerimiseAeg().toString();
    }
}
