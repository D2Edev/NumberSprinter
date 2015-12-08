package io.github.d2edev.numbersprinter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import io.github.d2edev.numbersprinter.Core.Person;

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
            view=inflater.inflate(io.github.d2edev.numbersprinter.R.layout.item_list_element,null, false);
            holder.ctvName = (TextView) view.findViewById(io.github.d2edev.numbersprinter.R.id.tvNameInList);
            holder.ctvScore = (TextView) view.findViewById(io.github.d2edev.numbersprinter.R.id.tvScoreInList);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        person = (Person) getItem(position);
        holder.ctvName.setText(person.getName());
        holder.ctvScore.setText(Integer.toString(person.getScoreTotal()));
        return view;
    }

    private class ViewHolder{
        TextView ctvName;
        TextView ctvScore;
    }
}
