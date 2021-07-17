import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Driver {
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        int points = 0;
        BaseRobot site = getSiteSelection();
        
        if (site != null) {
            points += guessCommonWords(site);
            String userText = getHeadlinesText();
            System.out.println("How many times it will appears in your opinion?");
            
            try {
            	int quantity = scanner.nextInt();
                points += checkText(quantity, userText, site);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("You achieved " + points + " points!");
        } else {
            System.out.println("Cant access the website..");
        }

    }

    private static int checkText(int quantity, String userText, BaseRobot site) throws IOException {
        int realQuantity = site.countInArticlesTitles(userText);
        if ((quantity - realQuantity) <= 2) {
            return 250;
        }
        return 0;
    }

    private static String getHeadlinesText() {
        String userText = "";
        System.out.println("Enter any text that you think should be appear in the headlines on the site, the text should be at most 20 symbols:");
        userText = scanner.nextLine();
        while (userText.length() < 1 || userText.length() > 20) {
            System.out.println("Wrong length! The length must be at most 20 symbols!");
        }
        return userText;
    }

    private static BaseRobot getSiteSelection() {
        int input = 0;
        
        System.out.println("Which site do you want to scan?");
        System.out.println(" 1 - Mako");
        System.out.println(" 2 - Ynet");
        System.out.println(" 3 - Walla");
        input = scanner.nextInt();
        
        scanner.nextLine();
        try {
            switch (input) {
                case 1:
                    return new MakoRobot();
                case 2:
                    return new YnetRobot();
                default:
                    return new WallaRobot();
            }
        } catch (IOException e) {
            System.out.println("Wrong choise!");
            getSiteSelection();
        }
        return null;
    }

    private static int guessCommonWords(BaseRobot site) {
        String guess;
        int points = 0;
        try {
            String longestArticle = site.getLongestArticleTitle();
            System.out.println("Guess what words are the most common on the site?");
            System.out.println("Prompt:\n" + longestArticle);
            Map<String, Integer> wordsInSite = site.getWordsStatistics();
            
            //wordsInSite.forEach((key, value) -> System.out.println(key + ":" + value)); // Used for checking the correctness of the count. Show the words and their counts.
            
            for (int i = 1; i <= 5; i++) {
                System.out.println("Try №" + i + ":");
                guess = scanner.nextLine();
                points += wordsInSite.getOrDefault(guess, 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return points;
    }

}
