package com.itproger.gw_android;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {


    private static final String db_name = "gw_android";
    private static final int db_version = 1;

    private static final  String db_table = "links";
    private static final  String db_link = "link";
    private static final  String db_link_name = "link_name";


    public DataBase(@Nullable Context context) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL);", db_table, db_link, db_link_name);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String  query = String.format("DELETE TABLE IF EXISTS %s", db_table);
        db.execSQL(query);
        onCreate(db);
    }

    public void insertData(String link, String link_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(db_link, link);
        values.put(db_link_name, link_name);
        //db.insertWithOnConflict(db_table,null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.insert(db_table,null,values);
    }

    public void deleteData(String link_name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(db_table, db_link_name + " = ?",new String[]{link_name});
        db.close();
    }

    public ArrayList<String> getAllTask (){
        ArrayList<String> allTask = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(db_table,null, null, null, null,null,null);
        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex(db_link_name);
            allTask.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return allTask;
    }

    public boolean checkLinkName(String link_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor check = db.rawQuery("SELECT * FROM " + db_table + " WHERE " + db_link_name +  " = '" + link_name + "' LIMIT 1 ", null);
        //SELECT * FROM `links`  WHERE link_name = 'git' LIMIT 1
        if(check.getCount() <= 0) {
            check.close();
            db.close();
            return false;
        } else {
            check.close();
            db.close();
            return true;
        }
    }

    @SuppressLint("Range")
    public String findLink(String link_name) {
        String rv = "not found";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor check = db.rawQuery("SELECT * FROM " + db_table + " WHERE " + db_link_name +  " = '" + link_name + "' LIMIT 1 ", null);
        //SELECT * FROM `links`  WHERE link_name = 'git' LIMIT
        if (check.moveToFirst()) {
            rv = check.getString(check.getColumnIndex(db_link));
        }
        check.close();
        db.close();
        return  rv;
    }


}
