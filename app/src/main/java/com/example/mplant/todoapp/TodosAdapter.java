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
import android.widget.TextView;

import java.util.ArrayList;

public class TodosAdapter extends RecyclerView.Adapter<TodosAdapter.MyViewHolder> {
    public interface OnUpdateTodoClickListener {
        void onDeleteTodoClicked(TodoItem todo);
        void onToggleCompleteTodoClicked(TodoItem todo);
    }
    private ArrayList<TodoItem> mDataset;
    private int REQUEST_CODE = 20;
    private OnUpdateTodoClickListener onUpdateTodoClickListener;
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
        void bind(final TodoItem todo){
            removeTodo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onUpdateTodoClickListener != null){
                        onUpdateTodoClickListener.onDeleteTodoClicked(todo);
                    }
                }
            });

            completeTodo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(onUpdateTodoClickListener != null) {
                        todo.setIsComplete(isChecked);
                        onUpdateTodoClickListener.onToggleCompleteTodoClicked(todo);
                    }
                }
            });
            todoText.setText(todo.getText());
        }
    }

    public TodosAdapter(OnUpdateTodoClickListener listener) {
        this.onUpdateTodoClickListener = listener;
    }

    @Override
    public TodosAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(final TodosAdapter.MyViewHolder holder, final int position) {
        TodoItem todo = mDataset.get(position);
        holder.bind(todo);
        holder.completeTodo.setChecked(todo.getIsComplete());
        holder.todoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                int position = holder.getAdapterPosition();
                TodoItem todo = mDataset.get(position);
                Intent i = new Intent(context, EditTodoActivity.class);
                i.putExtra("todo_text", todo.getText());
                i.putExtra("position", position);
                i.putExtra("create_date", todo.getCreateDate().getTime());
                i.putExtra("update_date", todo.getLastUpdated().getTime());
                ((Activity) context).startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    public void setTodos(ArrayList<TodoItem> todos){
        mDataset = todos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mDataset == null) {
            return 0;
        }
        return mDataset.size();
    }
}