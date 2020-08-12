package com.example.todoapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_TASK = "Task.db";
    public static final String TABLE_ITEMS = "Items";
    public static final String COL_ID = "itemID";
    public static final String COL_TASK = "Task";
    public static final String COL_STATUS = "Staus";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_TASK, null, 3);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_ITEMS +"( "+ COL_ID +"  integer PRIMARY KEY AUTOINCREMENT, "+ COL_TASK +" text,"+ COL_STATUS +" int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists "+ TABLE_ITEMS);
        onCreate(db);
    }

    public void addItem(String task){
        getWritableDatabase().execSQL("insert into "+ TABLE_ITEMS + "("+COL_TASK +" ," + COL_STATUS +")" + " values "+"('"+task+"'"+", 0);");
    }

    public void updateItem(int id, int status){
        getWritableDatabase().execSQL("update " + TABLE_ITEMS + " set " + COL_STATUS + " = "+ status + " where " + COL_ID + " = " + id );
    }

    public List<Items> getItems(){
        Cursor cursor= getWritableDatabase().query(TABLE_ITEMS,null,null,null,null,null,null);
        List<Items> items = new ArrayList<Items>();
        while(cursor.moveToNext()) {
            int itemId = cursor.getInt(
                    cursor.getColumnIndex(COL_ID)
            );
            String itemTask = cursor.getString(
                    cursor.getColumnIndex(COL_TASK));
            int itemStatus = cursor.getInt(
                    cursor.getColumnIndex(COL_STATUS));
            Items item = new Items();
            item.setId(itemId);
            item.setTask(itemTask);
            item.setStatus(itemStatus);

            items.add(item);
//            Log.d("OP", "getItems: "+item.task + item.status);
        }
        cursor.close();
        return items;
    }
}
