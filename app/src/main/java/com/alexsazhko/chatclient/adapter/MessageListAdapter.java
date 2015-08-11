package com.alexsazhko.chatclient.adapter;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexsazhko.chatclient.R;
import com.alexsazhko.chatclient.Utils;
import com.alexsazhko.chatclient.entity.ChatMessage;

import java.util.List;

public class MessageListAdapter extends BaseAdapter{

    private static final int TYPE_LEFT = 0;
    private static final int TYPE_RIGHT = 1;
    private static final int TYPE_MAX_COUNT = TYPE_RIGHT + 1;

    private List<ChatMessage> data;
    private Context context;

    public MessageListAdapter(List<ChatMessage> data, Context context) {
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
    public int getItemViewType(int position) {
        return data.get(position).isOwnMessage() ? TYPE_RIGHT  : TYPE_LEFT;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh = null;
        int type = getItemViewType(position);

        if (convertView == null) {
            vh = new ViewHolder();
            switch (type) {
                case TYPE_LEFT:
                    convertView = LayoutInflater.from(context).inflate(
                            R.layout.item_message_left, parent, false);

                    vh.msgContent = (TextView) convertView.findViewById(R.id.tvMsgContent);
                    vh.msgTime = (TextView) convertView.findViewById(R.id.tvMsgTime);
                    vh.image = (ImageView) convertView.findViewById(R.id.ivPicture);
                    break;
                case TYPE_RIGHT:
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.item_message_right, parent, false);

                    vh.msgContent = (TextView) convertView.findViewById(R.id.tvMsgContent);
                    vh.msgTime = (TextView) convertView.findViewById(R.id.tvMsgTime);
                    vh.image = (ImageView) convertView.findViewById(R.id.ivPicture);
                    break;
            }

           convertView.setTag(vh);

        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        vh.msgContent.setText(data.get(position).getMsgContent());
        vh.msgTime.setText(Utils.getShortestTimeFormat(data.get(position).getSendTime()));
        vh.image.setImageResource(Utils.getImageId("ic_contact.png", context));
        vh.image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_contact));

        return convertView;
    }

    private class ViewHolder{
        public TextView msgContent;
        public TextView msgTime;
        public ImageView image;

        public ViewHolder(){}

    }
}
