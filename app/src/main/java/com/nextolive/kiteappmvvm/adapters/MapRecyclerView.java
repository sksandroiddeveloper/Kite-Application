package com.nextolive.kiteappmvvm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.nextolive.kiteappmvvm.R;

import java.util.List;

public class MapRecyclerView extends RecyclerView.Adapter<MapRecyclerView.MyViewHolder> {

    private LayoutInflater inflater;
    Context ctx;

    public MapRecyclerView(Context ctx) {
        inflater = LayoutInflater.from(ctx);
        this.ctx = ctx;
    }

    @Override
    public MapRecyclerView.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.row_for_map_side_menu, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MapRecyclerView.MyViewHolder holder, final int position) {

        try {

            holder.cl_MapviewList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // Toast.makeText(ctx, "Testing!", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        // return dataList.size();
        return 3;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout cl_MapviewList;

        public MyViewHolder(View itemView) {
            super(itemView);
            cl_MapviewList = itemView.findViewById(R.id.cl_MapviewList);

        }

    }

}