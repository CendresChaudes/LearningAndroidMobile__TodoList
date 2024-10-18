package com.example.todolist;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Todo.class}, version = 2)
public abstract class TodoListDatabase extends RoomDatabase {

    private static final String DB_NAME = "todo_list.db";
    private static TodoListDatabase _instance;

    public static TodoListDatabase getInstance(Application application) {
        if (_instance == null) {
            _instance = Room.databaseBuilder(
                            application,
                            TodoListDatabase.class,
                            DB_NAME
                    )
                    .addMigrations(new TodoTableTodoListDatabaseMigration())
                    .build();
        }

        return _instance;
    }

    public abstract TodosDao todosDao();
}
