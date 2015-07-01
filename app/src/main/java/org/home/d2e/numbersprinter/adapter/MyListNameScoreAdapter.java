package org.home.d2e.numbersprinter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import org.home.d2e.numbersprinter.Core.Person;
import org.home.d2e.numbersprinter.R;

import java.util.List;

/**
 * Created by druzhyni on 29.06.2015.
 */
public class MyListNameScoreAdapter extends BaseAdapter {

    private List<Person> persons;
    private LayoutInflater inflater;
    private Context context;
    private Person person;

    public MyListNameScoreAdapter(Context context, @NonNull List<Person> persons){
        this.context=context;
        this.persons=persons;
        this.inflater=LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Object getItem(int position) {
        return persons.get(position);
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
            holder.ctvName = (TextView) view.findViewById(R.id.ctvName);
            holder.ctvScore = (TextView) view.findViewById(R.id.ctvScore);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        person = (Person) getItem(position);
        holder.ctvName.setText(person.getName());
        holder.ctvScore.setText(Integer.toString(person.getScore()));
        return view;
    }

    private class ViewHolder{
        TextView ctvName;
        TextView ctvScore;
    }
}
