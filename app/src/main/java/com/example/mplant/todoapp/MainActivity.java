package com.example.mplant.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Todo> todoItems;
    ArrayAdapter<Todo> aTodoAdapter;
    private RecyclerView.Adapter mAdapter;
    private EditText todoInput;
    private RecyclerView mRecyclerView;
    private int REQUEST_CODE = 20;
    private TodosState currentState = TodosState.ALL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todoInput = findViewById(R.id.editText);
        todoInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                v.clearFocus();
                addTodo(v);
                return false;
            }
        });
        this.initRecyclerView();
    }

    private void initRecyclerView(){
        mRecyclerView = findViewById(R.id.todosView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        todoItems = new ArrayList<>();
        mAdapter = new MyAdapter(todoItems);
        mRecyclerView.setAdapter(mAdapter);
        aTodoAdapter = new ArrayAdapter<>(this,android.R.layout.list_content, todoItems);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            int position = data.getExtras().getInt("position", -1);
            if(position >= 0){
                String todo_text = data.getExtras().getString("todo_text");
                Todo todo = todoItems.get(position);
                todo.setTodoText(todo_text);
                mAdapter.notifyItemChanged(position);
            }


        }
    }

    public void addTodo(View view){
        Editable todoTextField = todoInput.getText();
        String todoText = todoTextField.toString();
        if(todoText.length() > 0){
            Todo todo = new Todo(todoText);
            todoItems.add(todo);
            mAdapter.notifyItemInserted(todoItems.size() - 1);
            todoTextField.clear();
            todoInput.clearFocus();

        }
    }

    public Boolean getTodoVisibleState(Todo todo, TodosState state){
        switch (state){
            case ALL:
                return true;
            case ACTIVE:
                return !todo.getIsComplete();
            case COMPLETED:
                return todo.getIsComplete();
            default:
                return false;
        }
    }

    public void updateTodosVisibility(TodosState state){
        int i, size = todoItems.size();
        for(i = 0; i < size; i++){
            Todo todo = todoItems.get(i);
            Boolean shouldShowTodo = getTodoVisibleState(todo, state);
            todo.setVisible(shouldShowTodo);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void showAll(View v){
        updateTodosVisibility(TodosState.ALL);
    }

    public void showComplete(View v){
        updateTodosVisibility(TodosState.COMPLETED);
    }

    public void showActive(View v){
        updateTodosVisibility(TodosState.ACTIVE);
    }

}
