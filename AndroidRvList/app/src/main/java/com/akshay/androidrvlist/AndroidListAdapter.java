package com.akshay.androidrvlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by akshaynandwana on
 * 12, September, 2019
 **/
public class AndroidListAdapter extends RecyclerView.Adapter<AndroidListAdapter.MyViewHolder> {

    private AndroidVersion[] mDataset;

    public AndroidListAdapter(AndroidVersion[] myDataset) {
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(listItem);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position) {
        viewHolder.textView.setText(mDataset[position].getVersionName());
        viewHolder.imageView.setImageResource(mDataset[position].getmImageResourceId());
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.tvVersionName);
            this.imageView = (ImageView) itemView.findViewById(R.id.ivVersionImage);
        }
    }
}
