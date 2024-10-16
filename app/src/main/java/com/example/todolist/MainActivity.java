package com.example.todolist;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private LinearLayout _linearLayoutTodos;
    private FloatingActionButton _buttonCreateTodo;

    private ArrayList<Todo> _todos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this._initViews();

        _createMockTodos(10);
        _renderTodos();
    }

    private void _initViews() {
        this._linearLayoutTodos = findViewById(R.id.linearLayoutTodos);
        this._buttonCreateTodo = findViewById(R.id.buttonCreateTodo);
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

    private void _renderTodos() {
        for (Todo todo : this._todos) {
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

    private int _getColorIdByPriority(int priority) throws RuntimeException  {
        switch(priority) {
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