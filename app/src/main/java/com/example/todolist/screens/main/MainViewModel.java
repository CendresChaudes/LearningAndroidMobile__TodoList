package com.example.todolist.screens.main;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todolist.database.Todo;
import com.example.todolist.database.TodoListDatabase;
import com.example.todolist.database.TodosDao;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private final TodosDao _todosDao;
    private final CompositeDisposable _compositeDisposable;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this._todosDao = TodoListDatabase.getInstance(getApplication()).todosDao();
        this._compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        this._compositeDisposable.dispose();
    }

    public LiveData<List<Todo>> getTodos() {
        return this._todosDao.getTodos();
    }

    public void deleteTodo(Todo todo) {
        Disposable disposable = _todosDao
                .deleteTodo(todo.getId())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        new Action() {
                            @Override
                            public void run() throws Throwable {
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {
                                Log.e("MainViewModel", throwable.getMessage());
                            }
                        });

        this._compositeDisposable.add(disposable);
    }
}
