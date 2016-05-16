package com.group1.eda_397_group1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jesper on 2016-04-26.
 */
public class CustomListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Task> data;
    private static LayoutInflater inflater = null;



    public CustomListAdapter(Context context, ArrayList<Task> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.task_list_element, null);
        TextView taskName = (TextView) vi.findViewById(R.id.taskName);
        taskName.setText(data.get(position).getName());

        TextView taskOwner = (TextView) vi.findViewById(R.id.taskOwner);
        taskOwner.setText(data.get(position).getOwnerID());

        TextView taskDuration = (TextView) vi.findViewById(R.id.taskDuration);
        taskDuration.setText(data.get(position).getDuration() + "");

        TextView taskMember1 = (TextView) vi.findViewById(R.id.taskMember1);
        taskMember1.setText(data.get(position).getPairprogramer2D());

        //TextView taskMember2 = (TextView) vi.findViewById(R.id.taskMember2);
        //taskMember2.setText(data.get(position).getPairprogramer2D());
        return vi;
    }

    public void update(){
        this.notifyDataSetChanged();
        this.notifyDataSetInvalidated();


    }
}
