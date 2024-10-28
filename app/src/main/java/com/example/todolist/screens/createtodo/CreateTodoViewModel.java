package com.example.todolist.screens.createtodo;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todolist.database.Todo;
import com.example.todolist.database.TodoListDatabase;
import com.example.todolist.database.TodosDao;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CreateTodoViewModel extends AndroidViewModel {

    private final TodosDao _todosDao;
    private final MutableLiveData<Boolean> _shouldCloseScreen;
    private final CompositeDisposable _compositeDisposable;

    public CreateTodoViewModel(@NonNull Application application) {
        super(application);
        this._todosDao = TodoListDatabase.getInstance(application).todosDao();
        this._shouldCloseScreen = new MutableLiveData<>();
        this._compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        this._compositeDisposable.dispose();
    }

    public void createTodo(Todo todo) {
        Disposable disposable = _todosDao
                .createTodo(todo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action() {
                            @Override
                            public void run() throws Throwable {
                                _shouldCloseScreen.setValue(true);
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {
                                Log.e("CreateTodoViewModel", throwable.getMessage());
                            }
                        });

        this._compositeDisposable.add(disposable);
    }

    public LiveData<Boolean> getShouldCloseScreen() {
        return this._shouldCloseScreen;
    }
}
