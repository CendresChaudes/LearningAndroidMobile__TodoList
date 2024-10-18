package com.example.todolist;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TodoListDatabase _database;

    private RecyclerView _recyclerViewTodos;
    private TodosAdapter _todosAdapter;
    private FloatingActionButton _buttonCreateTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this._initActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this._renderTodos();
    }

    private void _launchCreateTodoScreen() {
        Intent intent = CreateTodoActivity.createIntent(this);
        startActivity(intent);
    }

    private void _initActivity() {
        this._initViews();
        this._initDb();
        this._initTodoAdapter();

        this._setupListeners();
        this._setupTodosRecyclerView();
    }

    private void _initViews() {
        this._recyclerViewTodos = findViewById(R.id.recyclerViewTodos);
        this._buttonCreateTodo = findViewById(R.id.buttonCreateTodo);
    }

    private void _initDb() {
        this._database = TodoListDatabase.getInstance(getApplication());
    }

    private void _initTodoAdapter() {
        _todosAdapter = new TodosAdapter();
    }

    private void _setupListeners() {
        this._setupCreateTodoButtonListener();
        this._setupTodoTouchHelper();
    }

    private void _setupCreateTodoButtonListener() {
        this._buttonCreateTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _launchCreateTodoScreen();
            }
        });
    }

    private void _setupTodoTouchHelper() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT
                ) {
                    @Override
                    public boolean onMove(
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            @NonNull RecyclerView.ViewHolder target
                    ) {
                        return false;
                    }

                    @Override
                    public void onSwiped(
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            int direction
                    ) {
                        int position = viewHolder.getAdapterPosition();
                        Todo todo = _todosAdapter.getTodos().get(position);
                        _database.todosDao().deleteTodo(todo.getId());
                        _renderTodos();
                    }
                });

        itemTouchHelper.attachToRecyclerView(_recyclerViewTodos);
    }

    private void _setupTodosRecyclerView() {
        this._recyclerViewTodos.setAdapter(this._todosAdapter);
    }

    private void _renderTodos() {
        this._todosAdapter.setTodos(this._database.todosDao().getTodos());
    }
}