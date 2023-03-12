package Kodutööd.Kodutöö5.TVStations;

import java.util.ArrayList;
import java.util.List;

public class PirateStation extends Broadcaster implements BroadcastListener {
    List<BroadcastListener> listeners = new ArrayList<>();
    @Override
    public void listen(String s) {
        broadcast(s);
    }

}
