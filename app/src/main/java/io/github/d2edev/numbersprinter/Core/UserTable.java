package io.github.d2edev.numbersprinter.Core;

/**
 * Created by druzhyni on 07.07.2015.
 */
public class UserTable {
    public static final String TABLE = "users";

    public static class Columns {


        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String LAST_SCORE = "last_score";
        public static final String SCORE_TOTAL = "ttl_score";
        public static final String SCORE_MAX = "max_score";
        public static final String GAMES_PLAYED = "qty_games";

    }

    public static final String CREATE_SQL = "CREATE TABLE "
            + TABLE + " ("
            + Columns.ID + " integer primary key autoincrement, "
            + Columns.NAME + " text, "
            + Columns.LAST_SCORE + " integer, "
            + Columns.SCORE_TOTAL + " integer, "
            + Columns.SCORE_MAX + " integer, "
            + Columns.GAMES_PLAYED + " integer)";

    public static final String DROP_SQL = "Drop table " + TABLE;
}
