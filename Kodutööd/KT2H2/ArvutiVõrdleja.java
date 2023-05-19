package Kodutööd.KT2H2;
import java.util.*;
@SuppressWarnings({"NonAsciiCharacters", "SpellCheckingInspection"})
public class ArvutiVõrdleja implements Comparator<Arvuti> {
    @Override
    public int compare(Arvuti arvuti1, Arvuti arvuti2) {
            int a1 = arvuti1.onKiirtöö() ? 1 : 0;
            int a2 = arvuti2.onKiirtöö() ? 1 : 0;
            return a2 - a1;
    }
}
