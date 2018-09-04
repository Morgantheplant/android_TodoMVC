package com.example.mplant.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditTodoActivity extends AppCompatActivity {
    EditText editTodoItem;
    Button saveChange;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);
        Intent intent = getIntent();
        editTodoItem = findViewById(R.id.editTodoText);
        saveChange = findViewById(R.id.saveChanges);
        String todo_text = intent.getStringExtra("todo_text");
        position = intent.getIntExtra("position", 0);
        editTodoItem.setText(todo_text);

    }

    public void onSubmit(View v) {
        String todo_text = editTodoItem.getText().toString();
        Intent data = new Intent();
        data.putExtra("todo_text", todo_text);
        data.putExtra("position", position);
        setResult(RESULT_OK, data);
        finish();
    }
}
