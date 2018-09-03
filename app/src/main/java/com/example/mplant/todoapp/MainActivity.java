package com.example.mplant.todoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Todo> todoItems;
    ArrayAdapter<Todo> aTodoAdapter;
    private RecyclerView.Adapter mAdapter;
    private EditText todoInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todoInput = findViewById(R.id.editText);
        this.initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView mRecyclerView = findViewById(R.id.todosView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        todoItems = new ArrayList<>();
        mAdapter = new MyAdapter(todoItems);
        mRecyclerView.setAdapter(mAdapter);
        aTodoAdapter = new ArrayAdapter<>(this,android.R.layout.list_content, todoItems);
    }

    public void addTodo(View view){
        Editable todoTextField = todoInput.getText();
        String todoText = todoTextField.toString();
        if(todoText.length() > 0){
            Todo todo = new Todo(todoText);
            todoItems.add(todo);
            mAdapter.notifyItemInserted(todoItems.size() - 1);
            todoTextField.clear();

        }
    }

}
