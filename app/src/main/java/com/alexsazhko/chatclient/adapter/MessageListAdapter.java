package com.alexsazhko.chatclient.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexsazhko.chatclient.R;
import com.alexsazhko.chatclient.Utils;
import com.alexsazhko.chatclient.entity.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class MessageListAdapter extends BaseAdapter{

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
    public View getView(int position, View convertView, ViewGroup parent) {

        final ChatMessage chatMessage = (ChatMessage) getItem(position);
        if (convertView == null) {
            if(!chatMessage.isOwnMessage()) {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.item_message_left, parent, false);
            }else{
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.item_message_right, parent, false);
            }

            TextView msgContent = (TextView) convertView.findViewById(R.id.tvMsgContent);
            TextView msgTime = (TextView) convertView.findViewById(R.id.tvMsgTime);
            ImageView image = (ImageView) convertView.findViewById(R.id.ivPicture);

            ViewHolder vh = new ViewHolder(msgContent, msgTime, image);

            convertView.setTag(vh);

        }

        ViewHolder vh = (ViewHolder) convertView.getTag();

        vh.msgContent.setText(data.get(position).getMsgContent());
        vh.msgTime.setText(Utils.getShortestTimeFormat(data.get(position).getSendTime()));
        vh.image.setImageResource(Utils.getImageId("ic_contact.png", context));
        //vh.image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_contact));

        return convertView;
    }

    private class ViewHolder{
        public final TextView msgContent;
        public final TextView msgTime;
        public final ImageView image;

        public ViewHolder (TextView msgContent, TextView msgTime, ImageView image){
            this.msgContent = msgContent;
            this.msgTime = msgTime;
            this.image = image;
        }


    }
}
