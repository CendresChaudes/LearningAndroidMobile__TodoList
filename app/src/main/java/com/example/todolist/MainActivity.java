package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Database _database = Database.getInstance();

    private RecyclerView _recyclerViewTodos;
    private TodosAdapter _todosAdapter = new TodosAdapter();
    private FloatingActionButton _buttonCreateTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this._initViews();

        this._setupCreateTodoButtonListener();
        this._setupTodosRecyclerView();
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

    private void _initViews() {
        this._recyclerViewTodos = findViewById(R.id.recyclerViewTodos);
        this._buttonCreateTodo = findViewById(R.id.buttonCreateTodo);
    }

    private void _setupCreateTodoButtonListener() {
        this._buttonCreateTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _launchCreateTodoScreen();
            }
        });
    }

    private void _setupTodosRecyclerView()  {
        this._recyclerViewTodos.setAdapter(this._todosAdapter);
    }

    private void _renderTodos() {
        this._todosAdapter.setTodos(this._database.getTodos());
    }
}