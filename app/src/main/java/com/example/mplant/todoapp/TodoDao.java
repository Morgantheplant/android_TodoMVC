package com.example.mplant.todoapp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TodoItem todo);

    @Query("DELETE FROM todo_table")
    void deleteAll();

    @Query("SELECT * from todo_table")
    LiveData<List<TodoItem>> getAllTodos();

    @Query("SELECT * from todo_table where complete = 1")
    LiveData<List<TodoItem>> getCompleteTodos();

    @Query("SELECT * from todo_table where complete = 0")
    LiveData<List<TodoItem>> getActiveTodos();

    @Delete
    void delete(TodoItem... todo);

    @Update
    void update(TodoItem... todo);

}
