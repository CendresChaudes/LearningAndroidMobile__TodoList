package com.example.todolist;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "todos")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int _id;

    @ColumnInfo(name = "text")
    @NotNull
    private String _text;

    @ColumnInfo(name = "priority")
    @NotNull
    private int _priority;

    public Todo(String text, int priority) {
        this._text = text;
        this._priority = priority;
    }

    public int getId() {
        return this._id;
    }

    public void setId(int id) {
        this._id = id;
    }


    public String getText() {
        return this._text;
    }

    public void setText(String text) {
        this._text = text;
    }

    public int getPriority() {
        return this._priority;
    }

    public void setPriority(int priority) {
        this._priority = priority;
    }
}
