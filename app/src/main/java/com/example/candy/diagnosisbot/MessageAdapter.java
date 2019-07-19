package com.example.candy.diagnosisbot;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends BaseAdapter{
    private Context mContext1;
    private List<messages> mMessageList;
    //Constructor


    public MessageAdapter(Context mContext1, List<messages> mMessageList) {
        this.mContext1 = mContext1;
        this.mMessageList = mMessageList;
    }

    @Override
    public int getCount() {
        return mMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v= View.inflate(mContext1, R.layout.your_messages, null);
        TextView username = (TextView) v.findViewById(R.id.TFself_username);
        TextView message= (TextView) v.findViewById(R.id.TFself_message);

        //Set text for TextView
        username.setText(mMessageList.get(position).getusername());
        message.setText((mMessageList.get(position).getmessage()));

        //Save product id to tag
        v.setTag(mMessageList.get(position).getId());

        return v;
    }
}
