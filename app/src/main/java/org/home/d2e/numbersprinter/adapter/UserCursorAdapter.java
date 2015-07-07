package org.home.d2e.numbersprinter.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.home.d2e.numbersprinter.Core.UserTable;
import org.home.d2e.numbersprinter.R;

/**
 * Created by druzhyni on 07.07.2015.
 */
public class UserCursorAdapter extends CursorAdapter {

    private LayoutInflater inflater;
    private  int INDEX_ID;
    private  int INDEX_NAME;
    private  int INDEX_PASSWORD;
    private  int INDEX_SCORE_TOTAL;
    private  int INDEX_SCORE_LAST;
    private  int INDEX_GAMES_PLAYED;
    private TextView tvName;
    private TextView tvScore;

    public UserCursorAdapter(Context context, Cursor c) {
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        INDEX_ID=c.getColumnIndex(UserTable.Columns.ID);
        INDEX_NAME=c.getColumnIndex(UserTable.Columns.NAME);
        INDEX_PASSWORD=c.getColumnIndex(UserTable.Columns.PASSWORD);
        INDEX_SCORE_TOTAL=c.getColumnIndex(UserTable.Columns.SCORE_TOTAL);
        INDEX_SCORE_LAST=c.getColumnIndex(UserTable.Columns.SCORE_LAST);
        INDEX_GAMES_PLAYED=c.getColumnIndex(UserTable.Columns.GAMES_PLAYED);


    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View rootView= inflater.inflate(R.layout.item_list_element, parent, false);
        return rootView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        tvName= (TextView) view.findViewById(R.id.tvNameInList);
        tvScore= (TextView) view.findViewById(R.id.tvScoreInList);
        tvName.setText(cursor.getString(INDEX_NAME));
        tvScore.setText(cursor.getString(INDEX_SCORE_TOTAL));
    }
}
