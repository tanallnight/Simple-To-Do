package com.example.simpletodo.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.simpletodo.R;
import com.example.simpletodo.adapters.ToDoInfoListAdapter;
import com.example.simpletodo.managers.DatabaseManager;
import com.example.simpletodo.models.ToDoItem;
import com.example.simpletodo.models.ToDoTitle;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private DatabaseManager databaseManager;
    private ToDoInfoListAdapter adapter;
    private ToDoTitle toDoTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toDoTitle = getIntent().getExtras().getParcelable("TITLE");

        getSupportActionBar().setTitle(toDoTitle.getTitle());

        databaseManager = new DatabaseManager(this);
        List<ToDoItem> toDoItems = databaseManager.getInfo(toDoTitle.getId());
        adapter = new ToDoInfoListAdapter(toDoItems, databaseManager);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                showAddDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAddDialog() {

        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setHint("Grocery List");

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Add new Objective")
                .setView(editText)
                .setPositiveButton(getResources().getString(R.string.action_add), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = editText.getText().toString();
                        ToDoItem toDoItem = new ToDoItem();
                        toDoItem.setId(toDoTitle.getId());
                        toDoItem.setMessage(text);
                        toDoItem.setIsChecked(false);
                        databaseManager.addInfo(toDoItem);
                        adapter.setData(databaseManager.getInfo(toDoTitle.getId()));
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.action_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }
}
