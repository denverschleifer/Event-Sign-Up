package com.example.cvtc.loginevent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Denver on 11/28/17.
 */

public class EventDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "EVENTData";
    private static final int DB_VER = 1;
    public static final String DB_TABLE = "Event";
    public static final String DB_COLUMN = "EventName";


    public EventDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE Event (_id INTEGER PRIMARY KEY AUTOINCREMENT, EventName TEXT NOT NULL);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DELETE TABLE IF EXISTS %s", DB_TABLE);
        db.execSQL(query);
        onCreate(db);
    }

    public void insertNewEvent(String event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN,event);
        db.insert(DB_TABLE,null,values);
        db.close();

    }

    public void deleteEvent(String event){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE,"EventName = ?", new String[] {event});
        db.close();
    }

    public ArrayList<String> getEventList() {
        ArrayList<String> eventList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[] {DB_COLUMN}, null, null, null, null, null);
        int index = cursor.getColumnIndex(DB_COLUMN);
        while (cursor.moveToNext()) {
            eventList.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return eventList;
    }
}

