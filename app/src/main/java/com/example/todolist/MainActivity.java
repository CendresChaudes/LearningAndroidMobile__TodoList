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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Database _database = Database.getInstance();

    private LinearLayout _linearLayoutTodos;
    private FloatingActionButton _buttonCreateTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this._initViews();

        this._setupCreateTodoButtonListener();
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
        this._linearLayoutTodos = findViewById(R.id.linearLayoutTodos);
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

    private void _renderTodos() {
        this._linearLayoutTodos.removeAllViews();

        for (Todo todo : this._database.getTodos()) {
            View view = getLayoutInflater().inflate(
                    R.layout.todo_item,
                    this._linearLayoutTodos,
                    false
            );

            TextView textViewTodo = view.findViewById(R.id.textViewTodo);

            textViewTodo.setText(todo.getText());
            int colorResId = this._getColorIdByPriority(todo.getPriority());
            int color = ContextCompat.getColor(this, colorResId);
            textViewTodo.setBackgroundColor(color);

            this._linearLayoutTodos.addView(view);
        }
    }

    private int _getColorIdByPriority(int priority) throws RuntimeException {
        switch (priority) {
            case 1:
                return R.color.green;
            case 2:
                return R.color.orange;
            case 3:
                return R.color.red;
            default:
                throw new RuntimeException("Priority doesn't support");
        }
    }
}