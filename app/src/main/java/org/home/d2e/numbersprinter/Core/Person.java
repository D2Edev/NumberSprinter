package org.home.d2e.numbersprinter.Core;

/**
 * Created by druzhyni on 30.06.2015.
 */
public class Person {

    private String name;
    private int password;
    private int scoreMax;
    private int scoreTotal;
    private int gamesPlayed;

    public Person() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public void setScoreMax(int scoreMax) {
        this.scoreMax = scoreMax;
    }

    public void setScoreTotal(int scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public Person(String name) {

        this.name = name;
        this.password=0;
        this.scoreMax =0;
        this.scoreTotal=0;
        this.gamesPlayed=0;
    }

    public Person(String name, int scoreTotal) {

        this.name = name;
        this.scoreTotal = scoreTotal;

    }

    public Person(String name, int password, int scoreMax, int scoreTotal, int gamesPlayed) {
        this.name = name;
        this.password = password;
        this.scoreMax = scoreMax;
        this.scoreTotal = scoreTotal;
        this.gamesPlayed = gamesPlayed;
    }

    public int getPassword() {
        return password;
    }

    public int getScoreMax() {
        return scoreMax;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }


    public String getName() {
        return name;
    }

    public int getScoreTotal() {
        return scoreTotal;
    }
}
