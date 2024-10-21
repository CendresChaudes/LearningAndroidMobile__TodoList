package com.example.todolist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface TodosDao {

    @Query("SELECT * FROM todos")
    LiveData<List<Todo>> getTodos();

    @Insert
    Completable createTodo(Todo todo);

    @Query("DELETE FROM todos WHERE id = :id")
    Completable deleteTodo(int id);
}
