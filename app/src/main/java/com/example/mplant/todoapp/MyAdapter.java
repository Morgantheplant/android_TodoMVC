package com.example.mplant.todoapp;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Todo> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout mTodoItem;
        public MyViewHolder(ConstraintLayout v) {
            super(v);
            mTodoItem = v;
        }
    }

    public MyAdapter(ArrayList<Todo> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TextView tv = (TextView) holder.mTodoItem.getViewById(R.id.todoText);
        if (tv != null) {
            Todo item = mDataset.get(position);
            tv.setText(item.getTodoText());
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}