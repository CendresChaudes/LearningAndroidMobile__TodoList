package com.example.todolist;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodosAdapter extends RecyclerView.Adapter<TodosAdapter.TodosViewHolder> {
    private ArrayList<Todo> _todos = new ArrayList<>();

    private IOnTodoClickListener _onTodoClickListener;

    public void setOnTodoClickListener(IOnTodoClickListener onTodoClickListener) {
        this._onTodoClickListener = onTodoClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTodos(ArrayList<Todo> todos) {
        this._todos = todos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TodosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.todo_item,
                parent,
                false
        );

        return new TodosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodosViewHolder holder, int position) {
        Todo todo = this._todos.get(position);
        TextView textViewTodo = holder.getTextViewTodo();

        textViewTodo.setText(todo.getText());
        int colorResId = this._getColorIdByPriority(todo.getPriority());
        int color = ContextCompat.getColor(holder.itemView.getContext(), colorResId);
        textViewTodo.setBackgroundColor(color);

        textViewTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_onTodoClickListener != null) {
                    _onTodoClickListener.onTodoClick(todo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this._todos.size();
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

    public class TodosViewHolder extends RecyclerView.ViewHolder {
        public TextView _textViewTodo;

        public TodosViewHolder(@NonNull View itemView) {
            super(itemView);
            this._textViewTodo = itemView.findViewById(R.id.textViewTodo);
        }

        public TextView getTextViewTodo() {
            return this._textViewTodo;
        }
    }

    interface IOnTodoClickListener {
        void onTodoClick(Todo todo);
    }
}
