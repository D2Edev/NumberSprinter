package io.github.d2edev.numbersprinter.Core;

/**
 * Created by druzhyni on 30.06.2015.
 */
public class Person {

    private String name;
    private int roundTime;
    private int scoreMax;
    private int scoreTotal;
    private int scoreLast;
    private int gamesPlayed;

    public Person() {    }

    public Person(String name) {       this.name = name;    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(int roundTime) {
        this.roundTime = roundTime;
    }

    public int getScoreMax() {
        return scoreMax;
    }

    public void setScoreMax(int scoreMax) {
        this.scoreMax = scoreMax;
    }

    public int getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(int scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public int getScoreLast() {
        return scoreLast;
    }

    public void setScoreLast(int scoreLast) {
        this.scoreLast = scoreLast;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public Person(String name, int scoreMax, int scoreTotal, int scoreLast, int gamesPlayed) {
        this.name = name;
        this.scoreMax = scoreMax;
        this.scoreTotal = scoreTotal;
        this.scoreLast = scoreLast;
        this.gamesPlayed = gamesPlayed;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", scoreMax=" + scoreMax +
                ", scoreTotal=" + scoreTotal +
                ", gamesPlayed=" + gamesPlayed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (roundTime != person.roundTime) return false;
        if (scoreMax != person.scoreMax) return false;
        if (scoreTotal != person.scoreTotal) return false;
        if (scoreLast != person.scoreLast) return false;
        if (gamesPlayed != person.gamesPlayed) return false;
        return name.equals(person.name);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + roundTime;
        result = 31 * result + scoreMax;
        result = 31 * result + scoreTotal;
        result = 31 * result + scoreLast;
        result = 31 * result + gamesPlayed;
        return result;
    }
}
