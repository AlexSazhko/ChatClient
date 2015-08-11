package com.alexsazhko.chatclient.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexsazhko.chatclient.R;
import com.alexsazhko.chatclient.utils.Utils;
import com.alexsazhko.chatclient.entity.Contact;

import java.util.ArrayList;

public class ContactListAdapter extends BaseAdapter{

    private ArrayList<Contact> data;
    private Context context;

    public ContactListAdapter(ArrayList<Contact> data, Context context) {
        this.data = data;
        this.context = context;
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
                    R.layout.item_contact, parent, false);


            TextView text = (TextView) convertView.findViewById(R.id.tvName);
            ImageView image = (ImageView) convertView.findViewById(R.id.ivIcon);

            ViewHolder vh = new ViewHolder(text, image);

            convertView.setTag(vh);

        }

        ViewHolder vh = (ViewHolder) convertView.getTag();

        vh.text.setText(data.get(position).getName());
        vh.image.setImageResource(Utils.getImageId("ic_contact.png"));
        //vh.image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_contact));

        return convertView;
    }

    private class ViewHolder{
        public final TextView text;
        public final ImageView image;

        public ViewHolder (TextView text, ImageView image){
            this.text = text;
            this.image = image;
        }


    }

}
