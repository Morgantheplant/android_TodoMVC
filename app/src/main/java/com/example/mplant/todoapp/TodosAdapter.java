package com.example.mplant.todoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Todo> mDataset;
    private int REQUEST_CODE = 20;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        View mTodoItem;
        Button removeTodo;
        TextView todoText;
        CheckBox completeTodo;
        private MyViewHolder(View v) {
            super(v);
            mTodoItem = v;
            removeTodo = v.findViewById(R.id.removeTodo);
            todoText = v.findViewById(R.id.todoText);
            completeTodo = v.findViewById(R.id.completeTodo);
        }
    }


    public MyAdapter(ArrayList<Todo> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(final MyAdapter.MyViewHolder holder, final int position) {
        Todo todo = mDataset.get(position);
        if(todo.getVisible()){
            holder.mTodoItem.setVisibility(View.VISIBLE);
            holder.itemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            holder.todoText.setText(todo.getTodoText());
            holder.completeTodo.setChecked(todo.getIsComplete());
            holder.removeTodo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteTodo(holder.getAdapterPosition());
                }
            });
            holder.completeTodo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    toggleSelected(holder.getAdapterPosition());
                }
            });
            holder.todoText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    int position = holder.getAdapterPosition();
                    Todo todo = mDataset.get(position);
                    Intent i = new Intent(context, EditTodoActivity.class);
                    i.putExtra("todo_text", todo.getTodoText());
                    i.putExtra("position", position);
                    ((Activity) context).startActivityForResult(i, REQUEST_CODE);
                }
            });
        } else {
            holder.mTodoItem.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new LinearLayout.LayoutParams(0,0));
        }
    }



    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void deleteTodo(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    private void toggleSelected(int index){
        Todo todo = mDataset.get(index);
        Boolean isComplete = todo.getIsComplete();
        todo.setIsComplete(!isComplete);
    }
}