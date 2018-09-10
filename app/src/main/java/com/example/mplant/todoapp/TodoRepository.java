package com.example.mplant.todoapp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class TodoRepository {
    private TodoDao mTodoDao;
    private LiveData<List<TodoItem>> mAllTodos;
    private LiveData<List<TodoItem>> mActiveTodos;
    private LiveData<List<TodoItem>> mCompleteTodos;
    TodoRepository(Application application) {
        TodoRoomDatabase db = TodoRoomDatabase.getDatabase(application);
        mTodoDao = db.todoDao();
        mAllTodos = mTodoDao.getAllTodos();
        mActiveTodos = mTodoDao.getActiveTodos();
        mCompleteTodos = mTodoDao.getCompleteTodos();

    }
    LiveData<List<TodoItem>> getAllTodos() {
        return mAllTodos;
    }
    LiveData<List<TodoItem>> getActiveTodos() {
        return mActiveTodos;
    }
    LiveData<List<TodoItem>> getCompleteTodos() {
        return mCompleteTodos;
    }
    public void insert (TodoItem todo) {
        new insertAsyncTask(mTodoDao).execute(todo);
    }
    public void update(TodoItem todo) {
        new updateAsyncTask(mTodoDao).execute(todo);
    }
    public void delete(TodoItem todo) {
        new deleteAsyncTask(mTodoDao).execute(todo);
    }

    private static class insertAsyncTask extends AsyncTask<TodoItem, Void, Void> {
        private TodoDao mAsyncTaskDao;
        insertAsyncTask(TodoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TodoItem... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<TodoItem, Void, Void> {

        private TodoDao mAsyncTaskDao;
        updateAsyncTask(TodoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TodoItem... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<TodoItem, Void, Void> {

        private TodoDao mAsyncTaskDao;
        deleteAsyncTask(TodoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TodoItem... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

}


