package com.example.mplant.todoapp;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import java.util.Date;
import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    private final MutableLiveData<TodosState> currentFilter;
    private TodoRepository mRepository;
    private  LiveData<List<TodoItem>> mTodoData;

    public TodoViewModel (Application application) {
        super(application);
        this.currentFilter = new MutableLiveData<>();
        mTodoData = Transformations.switchMap(currentFilter, new Function<TodosState, LiveData<List<TodoItem>>>() {
            @Override
            public LiveData<List<TodoItem>> apply(TodosState input) {
                switch (input){
                    case ACTIVE:
                         return mRepository.getActiveTodos();
                    case COMPLETED:
                        return mRepository.getCompleteTodos();
                    default:
                         return mRepository.getAllTodos();
                }
            }
        });
        mRepository = new TodoRepository(application);
    }

    LiveData<List<TodoItem>> getTodos() { return mTodoData; }

    public void setCurrentFilter(TodosState nextFilter){
        if(nextFilter != null && nextFilter != currentFilter.getValue()){
            this.currentFilter.setValue(nextFilter);
        }
    }
    public void insert(TodoItem todo) { mRepository.insert(todo); }
    public void update(TodoItem todo) {
        todo.setLastUpdated(new Date());
        mRepository.update(todo);
    }
    public void delete(TodoItem todo) { mRepository.delete(todo); }
}