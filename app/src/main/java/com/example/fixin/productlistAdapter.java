package com.example.fixin;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class productlistAdapter extends BaseAdapter
{
    private Context mContect;
    private List<item> mProductList;

    public productlistAdapter(Context mContect, List<item> mProductList) {
        this.mContect = mContect;
        this.mProductList = mProductList;
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContect, R.layout.activity_list_view, null);
        TextView List_item = (TextView)v.findViewById(R.id.List_item);
        List_item.setText(mProductList.get(position).getWork());

        return v;
    }
}
