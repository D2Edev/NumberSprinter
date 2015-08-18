package org.home.d2e.numbersprinter.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.home.d2e.numbersprinter.Core.GameField;
import org.home.d2e.numbersprinter.R;

import java.util.List;

/**
 * Created by druzhyni on 29.06.2015.
 */
public class GameFieldAdapter extends BaseAdapter {

    private List<GameField> gameFields;
    private LayoutInflater inflater;
    private Context context;
    private GameField gameField;
    private GradientDrawable drawable;
    private int unitSize;


    private String TAG = "TAG_MyFieldAdapter_";


    public GameFieldAdapter(Context context, @NonNull List<GameField> gameFields, int unitSize) {
        this.context = context;
        this.gameFields = gameFields;
        this.inflater = LayoutInflater.from(context);
        this.unitSize = unitSize;
    }


    @Override
    public int getCount() {
        return gameFields.size();
    }

    @Override
    public Object getItem(int position) {
        return gameFields.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = new ViewHolder();

        if (view == null) {
            view = inflater.inflate(R.layout.item_grid_element, null, false);
            holder.tvGameField = (TextView) view.findViewById(R.id.tvField);
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            llp.setMargins(unitSize, unitSize, unitSize, unitSize);
            holder.tvGameField.setLayoutParams(llp);
            holder.tvGameField.setTextSize((int)(unitSize*4.5));

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        gameField = (GameField) getItem(position);
        holder.tvGameField.setBackgroundResource(R.drawable.gamefield_design);
        holder.tvGameField.setText(Integer.toString(gameField.getFieldNumber()));
        holder.tvGameField.setTextColor(gameField.getFieldTextColor());
        drawable = (GradientDrawable) holder.tvGameField.getBackground();
        drawable.setColor(gameField.getFieldColor());

        return view;
    }

    private class ViewHolder {
        TextView tvGameField;
    }
}
