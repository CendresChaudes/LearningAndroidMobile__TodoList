package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel _viewModel;
    private TodosAdapter _todosAdapter;

    private RecyclerView _recyclerViewTodos;
    private FloatingActionButton _buttonCreateTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this._initActivity();
    }

    private void _launchCreateTodoScreen() {
        Intent intent = CreateTodoActivity.createIntent(this);
        startActivity(intent);
    }

    private void _initActivity() {
        this._initViews();
        this._initViewModel();
        this._initTodoAdapter();

        this._setupListeners();
        this._setupTodosRecyclerView();
        this._setupGetTodosObserver();
    }

    private void _initViews() {
        this._recyclerViewTodos = findViewById(R.id.recyclerViewTodos);
        this._buttonCreateTodo = findViewById(R.id.buttonCreateTodo);
    }

    private void _initViewModel() {
        this._viewModel = new ViewModelProvider(this).get(MainViewModel.class);
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

    private void _setupGetTodosObserver() {
        this._viewModel.getTodos().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                _todosAdapter.setTodos(todos);
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
                        _viewModel.deleteTodo(todo);
                    }
                });

        itemTouchHelper.attachToRecyclerView(_recyclerViewTodos);
    }

    private void _setupTodosRecyclerView() {
        this._recyclerViewTodos.setAdapter(this._todosAdapter);
    }
}