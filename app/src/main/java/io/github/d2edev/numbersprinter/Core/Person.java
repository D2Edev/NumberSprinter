package io.github.d2edev.numbersprinter.Core;

/**
 * Created by druzhyni on 30.06.2015.
 */
public class Person {

    private String name;
    private int roundTime;
    private int scoreMax;
    private int scoreTotal;
    private int gamesPlayed;

    public Person() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoundTime(int roundTime) {
        this.roundTime = roundTime;
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
        this.roundTime =0;
        this.scoreMax =0;
        this.scoreTotal=0;
        this.gamesPlayed=0;
    }

    public Person(String name, int scoreTotal) {

        this.name = name;
        this.scoreTotal = scoreTotal;

    }

    public Person(String name, int roundTime, int scoreMax, int scoreTotal, int gamesPlayed) {
        this.name = name;
        this.roundTime = roundTime;
        this.scoreMax = scoreMax;
        this.scoreTotal = scoreTotal;
        this.gamesPlayed = gamesPlayed;
    }

    public int getRoundTime() {
        return roundTime;
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
