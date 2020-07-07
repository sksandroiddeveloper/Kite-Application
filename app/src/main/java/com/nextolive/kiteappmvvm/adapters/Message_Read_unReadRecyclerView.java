package com.nextolive.kiteappmvvm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.nextolive.kiteappmvvm.R;

public class Message_Read_unReadRecyclerView extends RecyclerView.Adapter<Message_Read_unReadRecyclerView.MyViewHolder> {

    private LayoutInflater inflater;
    Context ctx;
    String isRead = "";

    public Message_Read_unReadRecyclerView(Context ctx, String isRead) {
        inflater = LayoutInflater.from(ctx);
        this.ctx = ctx;
        this.isRead = isRead;
    }

    @Override
    public Message_Read_unReadRecyclerView.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_message_read_unread, parent, false);
        Message_Read_unReadRecyclerView.MyViewHolder holder = new Message_Read_unReadRecyclerView.MyViewHolder(view);

        if (isRead != null && isRead.equalsIgnoreCase("YES")) {
            holder.iv_red_ball.setVisibility(View.GONE);
            holder.iv_user_profile.setColorFilter(ContextCompat.getColor(ctx, R.color.colorDarkBlue), android.graphics.PorterDuff.Mode.MULTIPLY);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(Message_Read_unReadRecyclerView.MyViewHolder holder, final int position) {

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        // return dataList.size();
        return 10;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_user_profile, iv_red_ball;

        public MyViewHolder(View itemView) {
            super(itemView);

            iv_red_ball = itemView.findViewById(R.id.iv_red_ball);
            iv_user_profile = itemView.findViewById(R.id.iv_user_profile);
        }

    }
}
