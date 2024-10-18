package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CreateTodoActivity extends AppCompatActivity {
    private TodoListDatabase _database;
    private Handler _handler;

    private EditText _editTextEnterTodoText;
    private RadioGroup _radioGroupPriorities;
    private RadioButton _radioButtonLowPriority;
    private RadioButton _radioButtonMediumPriority;
    private RadioButton _radioButtonHighPriority;
    private Button _buttonCreateTodo;

    @NonNull
    public static Intent createIntent(Context context) {
        return new Intent(context, CreateTodoActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo);
        this._initActivity();
    }

    private void _initActivity() {
        this._initViews();
        this._initDb();
        this._initHandler();

        this._setupListeners();
        this._setupPriorityByDefault();
    }

    private void _initViews() {
        this._editTextEnterTodoText = findViewById(R.id.editTextEnterTodoText);
        this._radioGroupPriorities = findViewById(R.id.radioGroupPriorities);
        this._radioButtonLowPriority = findViewById(R.id.radioButtonLowPriority);
        this._radioButtonMediumPriority = findViewById(R.id.radioButtonMediumPriority);
        this._radioButtonHighPriority = findViewById(R.id.radioButtonHighPriority);
        this._buttonCreateTodo = findViewById(R.id.buttonCreateTodo);
    }

    private void _initDb() {
        this._database = TodoListDatabase.getInstance(getApplication());
    }

    private void _initHandler() {
        this._handler = new Handler(Looper.getMainLooper());
    }

    private void _setupPriorityByDefault() {
        this._radioButtonLowPriority.setChecked(true);
    }

    private void _setupListeners() {
        this._setupCreateTodoButtonListener();
    }

    private void _setupCreateTodoButtonListener() {
        this._buttonCreateTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _createTodo();
            }
        });
    }

    private void _createTodo() {
        String text = this._editTextEnterTodoText.getText().toString().trim();
        int priority = this._getPriority();

        boolean isValid = !text.isEmpty() && priority != 0;

        if (isValid) {
            Todo todo = new Todo(text, priority);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    _database.todosDao().createTodo(todo);

                    _handler.post(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    });
                }
            }).start();
        } else {
            Toast.makeText(
                    CreateTodoActivity.this,
                    R.string.you_should_fill_all_values,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private int _getPriority() {
        int priority = 0;

        if (this._radioButtonLowPriority.isChecked()) {
            priority = 1;
        } else if (this._radioButtonMediumPriority.isChecked()) {
            priority = 2;
        } else if (this._radioButtonHighPriority.isChecked()) {
            priority = 3;
        }

        return priority;
    }
}