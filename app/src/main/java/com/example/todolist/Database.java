package com.example.todolist;

import java.util.ArrayList;
import java.util.Random;

public class Database {
    private static Database _instance;

    public static Database getInstance() {
        if (_instance == null) {
            _instance = new Database();
        }

        return _instance;
    }

    private ArrayList<Todo> _todos = new ArrayList<>();

    private Database() {
        this._createMockTodos(20);
    }

    public void add(Todo todo) {
        this._todos.add(todo);
    }

    public void remove(int id) {
        for (Todo todo : this._todos) {
            if (todo.getId() == id) {
                this._todos.remove(todo);
                break;
            }
        }
    }

    public ArrayList<Todo> getTodos() {
        return new ArrayList<>(this._todos);
    }

    public int size() {
        return this._todos.size();
    }

    private void _createMockTodos(int count) {
        Random randomPriority = new Random();

        for (int i = 0; i < count; i++) {
            Todo todo = new Todo(
                    i + 1,
                    "Todo № " + i,
                    randomPriority.nextInt(3) + 1
            );

            this._todos.add(todo);
        }
    }
}
