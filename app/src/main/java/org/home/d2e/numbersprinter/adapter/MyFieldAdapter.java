package org.home.d2e.numbersprinter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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
        holder.TextView.setBackgroundColor(context.getResources().getColor(R.color.game_field_yellow));
        return view;
    }

    private class ViewHolder{
        TextView TextView;
    }
}
