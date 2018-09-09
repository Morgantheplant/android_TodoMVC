package com.example.mplant.todoapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {

    private TodoRepository mRepository;

    private LiveData<List<TodoItem>> mAllTodos;

    public TodoViewModel (Application application) {
        super(application);
        mRepository = new TodoRepository(application);
        mAllTodos= mRepository.getAllTodos();
    }

    LiveData<List<TodoItem>> getAllTodos() { return mAllTodos; }

    public void insert(TodoItem todo) { mRepository.insert(todo); }
    public void update(TodoItem todo) { mRepository.update(todo); }
    public void delete(TodoItem todo) { mRepository.delete(todo); }
}