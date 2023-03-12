package Kodutööd.Kodutöö5.TVStations;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> foxNewsList = new ArrayList<String>(List.of(
                "Trump had a huge breakthrough",
                "Democrats have gone crazy",
                "When are we going to get even more money?",
                "China is gaining even more power",
                "China is losing even more power, and 9 more reasons why we need to elect Trump"
        ));
        List<String> northKoreanNationalNews = new ArrayList<String>(List.of(
                "The Great Leader Kim Jong-Un has met god",
                "900 facts why Kim Jong-Un is the best in the world",
                "The Great Leader Kim Jong-Un has invented a new sport",
                "Americans torturing regular citizens of North Korea",
                "Propaganda spread by the US leaders is horrifying"
        ));

        TVStation foxNews = new TVStation(foxNewsList);
        TVStation northKoreanNationalTV = new TVStation(northKoreanNationalNews);
        PirateStation pirates = new PirateStation();

        TV unTV = new TV("Kim Jong-Un");
        TV namTV = new TV("Kim Yong-Nam");
        TV pakTV = new TV("Pak Pong-Ju");

        // Kim Jong-Un
        foxNews.subscribe(unTV);
        northKoreanNationalTV.subscribe(unTV);

        // Kim Yong-Nam
        northKoreanNationalTV.subscribe(namTV);

        // Pak Pong-Ju
        pirates.subscribe(pakTV);

        // Pirate station
        foxNews.subscribe(pirates);
        northKoreanNationalTV.subscribe(pirates);

        foxNews.sendNews();
        northKoreanNationalTV.sendNews();
    }
}
