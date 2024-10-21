package com.example.todolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final TodosDao _todosDao;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this._todosDao = TodoListDatabase.getInstance(getApplication()).todosDao();
    }

    public LiveData<List<Todo>> getTodos() {
        return this._todosDao.getTodos();
    }

    public void deleteTodo(Todo todo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                _todosDao.deleteTodo(todo.getId());
            }
        }).start();
    }
}
