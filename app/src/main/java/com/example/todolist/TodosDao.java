package com.example.todolist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TodosDao {

    @Query("SELECT * FROM todos")
    LiveData<List<Todo>> getTodos();

    @Insert
    void createTodo(Todo todo);

    @Query("DELETE FROM todos WHERE id = :id")
    void deleteTodo(int id);
}
