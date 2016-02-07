package io.github.d2edev.numbersprinter.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.github.d2edev.numbersprinter.Core.GameField;

import java.util.List;

/**
 * Created by druzhyni on 29.06.2015.
 */
public class GameFieldAdapter extends BaseAdapter {

    private List<GameField> gameFields;
    private LayoutInflater inflater;

    private int unitSize;


    private String TAG = "TAG_MyFieldAdapter_";


    public GameFieldAdapter(Context context, @NonNull List<GameField> gameFields, int unitSize) {
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
        GameField gameField;
        GradientDrawable drawable;
        View view = convertView;
        ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(io.github.d2edev.numbersprinter.R.layout.item_grid_element, null, false);
            holder.tvGameField = (TextView) view.findViewById(io.github.d2edev.numbersprinter.R.id.tvField);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Log.d(TAG, "getView " + unitSize);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        llp.setMargins(0, unitSize, 0, 0);
        llp.width=unitSize*8;
        llp.height=unitSize*8;
        holder.tvGameField.setLayoutParams(llp);
        holder.tvGameField.setTextSize(TypedValue.COMPLEX_UNIT_PX,(unitSize*4));
        gameField = (GameField) getItem(position);
//        holder.tvGameField.setBackgroundResource(io.github.d2edev.numbersprinter.R.drawable.gamefield_design);
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
