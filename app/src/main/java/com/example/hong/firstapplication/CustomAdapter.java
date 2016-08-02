package com.example.hong.firstapplication;

/**
 * Created by Hong on 3/24/2016.
 */
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class    CustomAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public CustomAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(History h){
        list.add(h);
        super.add(h);
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public Object getItem(int position){
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HistoryHolder historyHolder;
        if (row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.custom_row,parent,false);
            historyHolder = new HistoryHolder();
            historyHolder.tx_date= (TextView)row.findViewById(R.id.tx_date);
            historyHolder.tx_endTime = (TextView) row.findViewById(R.id.tx_endTime);
            historyHolder.tx_startTime = (TextView) row.findViewById(R.id.tx_startTime);
            historyHolder.tx_subject = (TextView) row.findViewById(R.id.tx_subject);
            historyHolder.imageView = (ImageView) row.findViewById(R.id.imageView);
            row.setTag(historyHolder);
        }
        else
        {
            historyHolder = (HistoryHolder) row.getTag();
        }
        History history = (History)getItem(position);
        historyHolder.tx_subject.setText(history.get_subject());
        historyHolder.tx_startTime.setText(history.get_startTime());
        historyHolder.tx_endTime.setText(history.get_endTime());
        historyHolder.tx_date.setText(history.get_date());
        historyHolder.checked = history.get_checked();
        if (history.get_checked().equals("true")){
            historyHolder.imageView.setImageResource(R.drawable.ic_done_black_24dp);
        }
        else
        {
            historyHolder.imageView.setImageResource(R.drawable.ic_refresh_black_24dp);
        }
        historyHolder.instructor = history.get_instructor();
        return row;
    }

    static class HistoryHolder{
              TextView tx_date, tx_startTime, tx_endTime, tx_subject;
              ImageView imageView;
              String instructor, checked;
    }
}
