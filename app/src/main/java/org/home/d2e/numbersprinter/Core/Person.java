package org.home.d2e.numbersprinter.Core;

/**
 * Created by druzhyni on 30.06.2015.
 */
public class Person {

    private String name;
    private String password;
    private int scoreLast;
    private int scoreTotal;
    private int gamesPlayed;

    public Person() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setScoreLast(int scoreLast) {
        this.scoreLast = scoreLast;
    }

    public void setScoreTotal(int scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public Person(String name) {

        this.name = name;
        this.password="";
        this.scoreLast=0;
        this.scoreTotal=0;
        this.gamesPlayed=0;
    }

    public Person(String name, int scoreTotal) {

        this.name = name;
        this.scoreTotal = scoreTotal;

    }

    public Person(String name, String password, int scoreLast, int scoreTotal, int gamesPlayed) {
        this.name = name;
        this.password = password;
        this.scoreLast = scoreLast;
        this.scoreTotal = scoreTotal;
        this.gamesPlayed = gamesPlayed;
    }

    public String getPassword() {
        return password;
    }

    public int getScoreLast() {
        return scoreLast;
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
