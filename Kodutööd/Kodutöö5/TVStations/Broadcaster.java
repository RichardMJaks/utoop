package Kodutööd.Kodutöö5.TVStations;

import java.util.ArrayList;
import java.util.List;

public class Broadcaster {
    List<BroadcastListener> listeners = new ArrayList<>();
    void subscribe(BroadcastListener listener) {
        this.listeners.add(listener);
    }

    void broadcast(String s) {
        for (BroadcastListener listener : this.listeners)
            listener.listen(s);
    }
}
