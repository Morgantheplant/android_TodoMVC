package com.example.mplant.todoapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements TodosAdapter.OnUpdateTodoClickListener  {
    ArrayList<TodoItem> todoItems;
    ArrayAdapter<TodoItem> aTodoAdapter;
    private TodosAdapter mAdapter;
    private EditText todoInput;
    private int REQUEST_CODE = 20;
    private TodosState currentState = TodosState.ALL;
    private Button allButton;
    private Button activeButton;
    private Button completeButton;
    private TodoViewModel mTodoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todoInput = findViewById(R.id.editText);
        allButton = findViewById(R.id.all_button);
        activeButton = findViewById(R.id.active_button);
        completeButton = findViewById(R.id.complete_button);
        todoInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                v.clearFocus();
                addTodo(v);
                return false;
            }
        });
        RecyclerView mRecyclerView = findViewById(R.id.todosView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new TodosAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        aTodoAdapter = new ArrayAdapter<>(this,android.R.layout.list_content, todoItems);
        this.initRecyclerView();
        mTodoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        mTodoViewModel.getAllTodos().observe(this, new Observer<List<TodoItem>>() {
            @Override
            public void onChanged(@Nullable final List<TodoItem> todos) {
                todoItems = new ArrayList<>();
                todoItems.addAll(todos);

                mAdapter.setTodos(todoItems);
            }
        });

    }

    private void initRecyclerView(){
        RecyclerView mRecyclerView = findViewById(R.id.todosView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        todoItems = new ArrayList<>();
        mAdapter = new TodosAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        aTodoAdapter = new ArrayAdapter<>(this,android.R.layout.list_content, todoItems);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            int position = data.getExtras().getInt("position", -1);
            if(position >= 0){
                String todo_text = data.getExtras().getString("todo_text");
                TodoItem todo = todoItems.get(position);
                todo.setText(todo_text);
                mTodoViewModel.update(todo);
            }
        }
    }

    @Override
    public void onDeleteTodoClicked(TodoItem todo) {
        mTodoViewModel.delete(todo);
    }

    @Override
    public void onToggleCompleteTodoClicked(TodoItem todo) {
        mTodoViewModel.update(todo);
    }

    public void addTodo(View view){
        Editable todoTextField = todoInput.getText();
        String todoText = todoTextField.toString();
        if(todoText.length() > 0){
            TodoItem todo = new TodoItem(todoText);
            mTodoViewModel.insert(todo);
        }
    }

//    public Boolean getTodoVisibleState(Todo todo){
//        switch (currentState){
//            case ALL:
//                return true;
//            case ACTIVE:
//                return !todo.getIsComplete();
//            case COMPLETED:
//                return todo.getIsComplete();
//            default:
//                return false;
//        }
//    }
//
//    public void updateTodosVisibility(TodosState state){
//        setCurrentState(state);
//    }


    private void setCurrentState(TodosState state){
        this.currentState = state;
    }

    public void showAll(View v){
        if(TodosState.ALL != currentState){
            v.setBackgroundResource(R.drawable.active_button_background);
//            updateTodosVisibility(TodosState.ALL);
            int color = ContextCompat.getColor(this, android.R.color.transparent);
            completeButton.setBackgroundColor(color);
            activeButton.setBackgroundColor(color);
        }
    }

    public void showComplete(View v){
        if(TodosState.COMPLETED != currentState){
            v.setBackgroundResource(R.drawable.active_button_background);
//            updateTodosVisibility(TodosState.COMPLETED);
            int color = ContextCompat.getColor(this, android.R.color.transparent);
            activeButton.setBackgroundColor(color);
            allButton.setBackgroundColor(color);
        }
    }

    public void showActive(View v){
        if(TodosState.ACTIVE != currentState){
            v.setBackgroundResource(R.drawable.active_button_background);
//            updateTodosVisibility(TodosState.ACTIVE);
            int color = ContextCompat.getColor(this, android.R.color.transparent);
            completeButton.setBackgroundColor(color);
            allButton.setBackgroundColor(color);
        }

    }
}
