package com.example.exam_task.services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.exam_task.R;
import com.example.exam_task.models.ResourceInfo;


import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private List<ResourceInfo> files;

    public void refresh (List<ResourceInfo> ri){
        files.clear();
        files.addAll(ri);
        notifyDataSetChanged();
    }
    public ListViewAdapter (Context context){
        this.context = context;
        this.files=new ArrayList<>();

    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public Object getItem(int position) {
        return files.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.subcategories_list_item, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.tv_name);
        ResourceInfo resourceInfo = files.get(position);
        name.setText(resourceInfo.name);
        return convertView;
    }
}
