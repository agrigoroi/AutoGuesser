package main;

/**
 * Created by Alexandru Grigoroi on 09/02/14.
 */
public class Player {
    private String name;
    private String id;
    private int totalScore;
    private int numberOfGames;

    private static String getRandomName() {
        return "HI";
    }

    private static String generateCookie() {
        
    }

    public Player() {
        name = getRandomName();
        id = generateCookie();
        totalScore = 0;
        numberOfGames = 0;
    }

    public Player(String name, String id, int totalScore, int numberOfGames) {
        this.name = name;
        this.id = id;
        this.totalScore = totalScore;
        this.numberOfGames = numberOfGames;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }
}
