package com.alexsazhko.chatclient.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alexsazhko.chatclient.R;
import com.alexsazhko.chatclient.Utils;
import com.alexsazhko.chatclient.entity.ChatMessage;

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
        LinearLayout messageContainer;
         final ChatMessage chatMessage = (ChatMessage) getItem(position);

        ViewHolder vh = null;
       // if (convertView == null) {
            vh = new ViewHolder();
            Log.i("DEBUG:", "message in adapter is " + chatMessage.getMsgContent() + " " + chatMessage.isOwnMessage());
            if(!chatMessage.isOwnMessage()) {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.item_message_left, parent, false);
               /* vh.messageContainer = (LinearLayout) convertView.findViewById(R.id.llMessageItemLeft);
                vh.msgContent = (TextView) convertView.findViewById(R.id.tvMsgContent);
                vh.msgTime = (TextView) convertView.findViewById(R.id.tvMsgTime);
                vh.image = (ImageView) convertView.findViewById(R.id.ivPicture);*/
            }else{
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.item_message_right, parent, false);
               /* vh.messageContainer = (LinearLayout) convertView.findViewById(R.id.llMessageItemRight);
                vh.msgContent = (TextView) convertView.findViewById(R.id.tvMsgContent);
                vh.msgTime = (TextView) convertView.findViewById(R.id.tvMsgTime);
                vh.image = (ImageView) convertView.findViewById(R.id.ivPicture);*/
            }

           /* TextView msgContent = (TextView) convertView.findViewById(R.id.tvMsgContent);
            TextView msgTime = (TextView) convertView.findViewById(R.id.tvMsgTime);
            ImageView image = (ImageView) convertView.findViewById(R.id.ivPicture);*/

            //ViewHolder vh = new ViewHolder(messageContainer, msgContent, msgTime, image);

           // convertView.setTag(vh);

      //  }

        //ViewHolder vh = (ViewHolder) convertView.getTag();
        //vh = (ViewHolder) convertView.getTag();

       /* vh.msgContent.setText(data.get(position).getMsgContent());
        vh.msgTime.setText(Utils.getShortestTimeFormat(data.get(position).getSendTime()));
        vh.image.setImageResource(Utils.getImageId("ic_contact.png", context));*/
        //vh.image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_contact));
        TextView msgContent = (TextView) convertView.findViewById(R.id.tvMsgContent);
        TextView msgTime = (TextView) convertView.findViewById(R.id.tvMsgTime);
        ImageView image = (ImageView) convertView.findViewById(R.id.ivPicture);
        msgContent.setText(data.get(position).getMsgContent());
        msgTime.setText(Utils.getShortestTimeFormat(data.get(position).getSendTime()));
        image.setImageResource(Utils.getImageId("ic_contact.png", context));

        return convertView;
    }

    private class ViewHolder{
        public LinearLayout messageContainer;
        public TextView msgContent;
        public TextView msgTime;
        public ImageView image;

        public ViewHolder(){}

        public ViewHolder (LinearLayout messageContainer, TextView msgContent, TextView msgTime, ImageView image){
            this.messageContainer = messageContainer;
            this.msgContent = msgContent;
            this.msgTime = msgTime;
            this.image = image;
        }


    }
}
