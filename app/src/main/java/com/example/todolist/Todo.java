package com.example.todolist;

public class Todo {
    private int _id;
    private String _text;
    private int _priority;

    public Todo(int id, String text, int priority) {
        this._id = id;
        this._text = text;
        this._priority = priority;
    }

    public int getId() {
        return this._id;
    }

    public String getText() {
        return this._text;
    }

    public int getPriority() {
        return this._priority;
    }
}
