package com.alexsazhko.chatclient.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexsazhko.chatclient.ChatApplication;
import com.alexsazhko.chatclient.R;
import com.alexsazhko.chatclient.entity.Contact;
import com.alexsazhko.chatclient.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class AddContactListAdapter extends BaseAdapter{

    private static Context context = ChatApplication.getInstance();

    private ArrayList<Contact> data;

    public AddContactListAdapter(ArrayList<Contact> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_add_contact, parent, false);


            TextView text = (TextView) convertView.findViewById(R.id.tvAddName);

            ViewHolder vh = new ViewHolder(text);

            convertView.setTag(vh);

        }

        ViewHolder vh = (ViewHolder) convertView.getTag();

        vh.text.setText(data.get(position).getName());

        return convertView;
    }

    private class ViewHolder{
        public final TextView text;

        public ViewHolder (TextView text){
            this.text = text;
        }


    }

}
