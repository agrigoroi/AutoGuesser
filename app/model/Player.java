package model;

import java.util.Random;

/**
 * Created by Alexandru Grigoroi on 09/02/14.
 */
public class Player {

    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random rnd = new Random();

    private String name;
    private String id;
    private int totalScore;
    private int numberOfGames;

    private static String getRandomName() {
        return generateRandomString(8);
    }

    private static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for( int i = 0; i < length; i++ )
                sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    private static String generateCookie() {
        return generateRandomString(30);
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

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }
    
    public void setName(String name){
    	this.name = name;
    }
}
