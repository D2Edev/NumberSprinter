package org.home.d2e.numbersprinter.Core;

/**
 * Created by druzhyni on 07.07.2015.
 */
public class UserTable {
    public static final String TABLE = "users";

    public static class Columns {


        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String PASSWORD = "password";
        public static final String SCORE_TOTAL = "tscore";
        public static final String SCORE_LAST = "lcsore";
        public static final String GAMES_PLAYED = "gplayed";

    }

    public static final String CREATE_SQL = "CREATE TABLE "
            + TABLE + " ("
            + Columns.ID + " integer primary key autoincrement, "
            + Columns.NAME + " text, "
            + Columns.PASSWORD + " text, "
            + Columns.SCORE_TOTAL + " integer, "
            + Columns.SCORE_LAST + " integer, "
            + Columns.GAMES_PLAYED + " integer)";

    public static final String DROP_SQL = "Drop table " + TABLE;
}
