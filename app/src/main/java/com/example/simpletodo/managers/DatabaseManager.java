package com.example.simpletodo.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.simpletodo.models.ToDoItem;
import com.example.simpletodo.models.ToDoTitle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "todoDB";

    private static final String TABLE_TITLE = "titleTable";
    private static final String TABLE_INFO = "infoTable";

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String MESSAGE = "message";
    private static final String CHECKED = "checked";

    private static final String CREATE_TITLE_TABLE =
            "CREATE TABLE " + TABLE_TITLE + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TITLE + " TEXT" + ")";
    private static final String CREATE_INFO_TABLE =
            "CREATE TABLE " + TABLE_INFO + "(" +
                    ID + " INTEGER," +
                    MESSAGE + " TEXT," +
                    CHECKED + " INTEGER" + ")";
    private static final String DELETE_TITLE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_TITLE;
    private static final String DELETE_INFO_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_INFO;


    public DatabaseManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TITLE_TABLE);
        db.execSQL(CREATE_INFO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TITLE_TABLE);
        db.execSQL(DELETE_INFO_TABLE);
        onCreate(db);
    }

    public void addTitle(ToDoTitle toDoTitle) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, toDoTitle.getTitle());
        getWritableDatabase().insert(TABLE_TITLE, null, contentValues);
        contentValues.clear();
    }

    public List<ToDoTitle> getAllTitles() {
        List<ToDoTitle> toDoTitles = Collections.emptyList();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_TITLE, null);
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            toDoTitles = new ArrayList<>();
            do {
                ToDoTitle toDoTitle = new ToDoTitle();
                toDoTitle.setId(cursor.getInt(0));
                toDoTitle.setTitle(cursor.getString(1));
                toDoTitles.add(toDoTitle);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return toDoTitles;
    }

    public ToDoTitle getTitle(int id) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_TITLE + " WHERE " + ID + " = ?",
                new String[]{String.valueOf(id)});
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            ToDoTitle toDoTitle = new ToDoTitle();
            toDoTitle.setId(cursor.getInt(0));
            toDoTitle.setTitle(cursor.getString(1));
            cursor.close();
            return toDoTitle;
        }
        return null;
    }

    public void deleteTitle(int id) {
        getWritableDatabase().delete(TABLE_TITLE, ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void addInfo(ToDoItem toDoItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, toDoItem.getId());
        contentValues.put(MESSAGE, toDoItem.getMessage());
        contentValues.put(CHECKED, toDoItem.isChecked() ? 1 : 0);
        getWritableDatabase().insert(TABLE_INFO, null, contentValues);
        contentValues.clear();
    }

    public List<ToDoItem> getInfo(int id) {
        List<ToDoItem> toDoItems = Collections.emptyList();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_INFO + " WHERE " + ID + " = ?",
                new String[]{String.valueOf(id)});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            toDoItems = new ArrayList<>();
            do {
                ToDoItem toDoItem = new ToDoItem();
                toDoItem.setId(cursor.getInt(0));
                toDoItem.setMessage(cursor.getString(1));
                toDoItem.setIsChecked(cursor.getInt(2) == 1);
                toDoItems.add(toDoItem);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return toDoItems;
    }

    public void changeChecked(ToDoItem toDoItem, boolean checked) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CHECKED, checked);
        getWritableDatabase().update(TABLE_INFO, contentValues, ID + " = ? AND " + MESSAGE + " = ?"
                , new String[]{String.valueOf(toDoItem.getId()), toDoItem.getMessage()});
    }

    public void deleteInfo(ToDoItem toDoItem) {
        getWritableDatabase().delete(TABLE_INFO, ID + " = ? AND " + MESSAGE + " = ?",
                new String[]{String.valueOf(toDoItem.getId()), toDoItem.getMessage()});
    }
}
