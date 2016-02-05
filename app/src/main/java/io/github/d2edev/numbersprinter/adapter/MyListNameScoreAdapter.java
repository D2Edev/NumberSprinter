package io.github.d2edev.numbersprinter.adapter;

import android.content.Context;
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
    private boolean defined = false;
    private int defaultColor = 0;
    private int selectionColor = 0;
    private int selectedID = -1;
    private View prevView;


    public MyListNameScoreAdapter(Context context, List<Person> persons) {
        this.persons = persons;
        inflater = LayoutInflater.from(context);
    }

    public boolean isBckColorDEFINED() {
        return defined;
    }

    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Person getItem(int position) {
        return persons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(io.github.d2edev.numbersprinter.R.layout.item_list_element, null, false);
            holder.ctvName = (TextView) view.findViewById(io.github.d2edev.numbersprinter.R.id.tvNameInList);
            holder.ctvScore = (TextView) view.findViewById(io.github.d2edev.numbersprinter.R.id.tvScoreInList);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (position == selectedID) {
            view.setBackgroundColor(selectionColor);
            prevView=view;
        } else {
            view.setBackgroundColor(defaultColor);
        }
        holder.ctvName.setText(getItem(position).getName());
        holder.ctvScore.setText(Integer.toString(getItem(position).getScoreTotal()));
        return view;
    }

    public int getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(int defaultColor) {
        this.defaultColor = defaultColor;
        defined = true;

    }

    public int getSelectionColor() {
        return selectionColor;
    }

    public void setSelectionColor(int selectionColor) {
        this.selectionColor = selectionColor;
    }

    public void setSelectedID(int selectedID) {
        this.selectedID = selectedID;
    }

    public int getSelectedID() {
        return selectedID;
    }

    public View getPrevView() {
        return prevView;
    }

    public void setPrevView(View prevView) {
        this.prevView = prevView;
    }



    private class ViewHolder {
        TextView ctvName;
        TextView ctvScore;
    }
}
