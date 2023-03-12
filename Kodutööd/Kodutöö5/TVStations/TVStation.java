package Kodutööd.Kodutöö5.TVStations;
import Custom.OOPFunctions;

import java.util.ArrayList;
import java.util.List;

public class TVStation extends Broadcaster {
    private List<String> newsList;
    List<BroadcastListener> listeners = new ArrayList<>();
    public TVStation (List<String> news) {
        this.newsList = news;
    }

    void sendNews () {
        int i = OOPFunctions.randInt(0, newsList.size());
        this.broadcast(newsList.get(i));
    }
}
