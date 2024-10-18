package com.example.todolist;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class TodoTableTodoListDatabaseMigration extends Migration {
    public TodoTableTodoListDatabaseMigration() {
        super(1, 2);
    }

    @Override
    public void migrate(SupportSQLiteDatabase database) {
        database.execSQL("CREATE TABLE new_table (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "text TEXT NOT NULL," +
                "priority INTEGER NOT NULL)");

        database.execSQL("INSERT INTO new_table (id, text, priority) " +
                "SELECT _id, _text, _priority FROM todos");

        database.execSQL("DROP TABLE todos");

        database.execSQL("ALTER TABLE new_table RENAME TO todos");
    }
}
