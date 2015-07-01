package org.home.d2e.numbersprinter.Core;

import android.support.annotation.Nullable;

/**
 * Created by druzhyni on 30.06.2015.
 */
public class Person {

        private String name;
        private int score;

        public Person (String name, int score) {
            this.name = name;
            this.score = score;
        }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
