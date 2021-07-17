import java.io.IOException;
import java.util.Map;

public interface MapOrder {
    String accessSite(String site) throws IOException;

    default Map<String, Integer> getWordsIntoMap(String[] wordsOfArticle, Map<String, Integer> map) {
        int counter;
        for (String word : wordsOfArticle) {
            if (map.containsKey(word)) {
            	counter = map.get(word) + 1;
            } else {
            	counter = 1;
            }
            map.put(word, counter);
        }
        return map;
    }

    default String correctWords(String siteText) {
        siteText = siteText.replaceAll("[-–•<>@&_%():,.?0-9]", " ");
        siteText = siteText.replaceAll("\"\\s|\\s\"", " ");
        siteText = siteText.replaceAll("\\s+", " ");
        return siteText;
    }

}
