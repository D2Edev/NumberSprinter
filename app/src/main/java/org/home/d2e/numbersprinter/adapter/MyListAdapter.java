package org.home.d2e.numbersprinter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import org.home.d2e.numbersprinter.R;

import java.util.List;

/**
 * Created by druzhyni on 29.06.2015.
 */
public class MyListAdapter extends BaseAdapter {

    private List<String> names;
    private LayoutInflater inflater;
    private Context context;
    private String item;

    public MyListAdapter(Context context, @NonNull List<String> names){
        this.context=context;
        this.names=names;
        this.inflater=LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
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
            view=inflater.inflate(R.layout.item_list_element,null, false);
            holder.checkedTextView = (CheckedTextView) view.findViewById(R.id.ctvName);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        item = (String) getItem(position);
        holder.checkedTextView.setText(item);
        return view;
    }

    private class ViewHolder{
        CheckedTextView checkedTextView;
    }
}
