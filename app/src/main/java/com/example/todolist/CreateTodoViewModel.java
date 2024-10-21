package com.example.todolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CreateTodoViewModel extends AndroidViewModel {

    private TodosDao _todosDao;
    private MutableLiveData<Boolean> _shouldCloseScreen;

    public CreateTodoViewModel(@NonNull Application application) {
        super(application);
        this._todosDao = TodoListDatabase.getInstance(application).todosDao();
        this._shouldCloseScreen = new MutableLiveData<>();
    }

    public void createTodo(Todo todo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                _todosDao.createTodo(todo);
                _shouldCloseScreen.postValue(true);
            }
        }).start();
    }

    public LiveData<Boolean> getShouldCloseScreen() {
        return this._shouldCloseScreen;
    }
}
