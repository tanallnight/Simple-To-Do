package com.example.simpletodo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.simpletodo.R;
import com.example.simpletodo.managers.DatabaseManager;
import com.example.simpletodo.models.ToDoItem;

import java.util.List;

public class ToDoInfoListAdapter extends RecyclerView.Adapter<ToDoInfoListAdapter.ViewHolder> {

    List<ToDoItem> toDoItems;
    DatabaseManager databaseManager;

    public ToDoInfoListAdapter(List<ToDoItem> toDoItems, DatabaseManager databaseManager) {
        this.toDoItems = toDoItems;
        this.databaseManager = databaseManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.message.setText(toDoItems.get(position).getMessage());
        holder.checkBox.setChecked(toDoItems.get(position).isChecked());
    }

    public void setData(List<ToDoItem> items) {
        this.toDoItems = items;
    }

    @Override
    public int getItemCount() {
        return toDoItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

        private TextView message;
        private CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.text);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            checkBox.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int position = getAdapterPosition();
            toDoItems.get(position).setIsChecked(isChecked);
            databaseManager.changeChecked(toDoItems.get(position), isChecked);
        }
    }

}
