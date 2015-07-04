package org.home.d2e.numbersprinter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import org.home.d2e.numbersprinter.R;

import java.util.List;

/**
 * Created by druzhyni on 29.06.2015.
 */
public class MyFieldAdapter extends BaseAdapter {

    private List<Integer> gameFields;
    private LayoutInflater inflater;
    private Context context;
    private int gameField;
    private int clr_r;
    private int clr_g;
    private int clr_b;
    private String TAG = "TAG_MyFieldAdapter_";


    public MyFieldAdapter(Context context, @NonNull List<Integer> gameFields){
        this.context=context;
        this.gameFields=gameFields;
        this.inflater=LayoutInflater.from(context);
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
        if (view==null){
            view=inflater.inflate(R.layout.item_grid_element,null, false);
            holder.TextView = (TextView) view.findViewById(R.id.tvField);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        gameField = (int) getItem(position);
        holder.TextView.setText(Integer.toString(gameField));
        //holder.TextView.setBackgroundColor(context.getResources().getColor(R.color.game_field_yellow));
        clr_r = (int)(Math.random()*235+20);
        clr_g = (int)(Math.random()*235+20);
        clr_b = (int)(Math.random()*235+20);
        holder.TextView.setBackgroundColor(Color.rgb(clr_r,clr_g, clr_b));
        Log.d(TAG,"" + clr_r + " "+ clr_g + " " + clr_b);
        return view;
    }

    private class ViewHolder{
        TextView TextView;
    }
}
