package com.gadg.sahtifiyadi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.gadg.sahtifiyadi.R;
import com.gadg.sahtifiyadi.database.TablesColumnsNames.Speciality_columns;
import com.gadg.sahtifiyadi.items.Speciality;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.gadg.sahtifiyadi.database.TablesColumnsNames.Speciality_columns.*;
import static com.gadg.sahtifiyadi.database.TablesColumnsNames.TABLE_NAME_SPECIALITY;


public class DBManagerSpeciality {
     private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManagerSpeciality(Context c) {
        context = c;
    }

    public DBManagerSpeciality open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public boolean CheckIsDataAlreadyInDBorNot(String nameFeild) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String Query = "Select * from " + TABLE_NAME_SPECIALITY + " where " + _NAME + " = '" + nameFeild +"' ";
            Cursor cursor = db.rawQuery(Query, null);
            if(cursor.moveToFirst()) {
                cursor.close();
                return true;
            }
            cursor.close();
        return false;
    }
	
    public String getNumberSpec(String nameFeild) {
        String number ="0";
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String Query = "Select * from " + TABLE_NAME_SPECIALITY + " where " + _NAME + " = '" + nameFeild +"' ";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()) {
            number= cursor.getString(2);
        }
        cursor.close();
        return number;
    }


    public void insert(long _id,String name, String count) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(_ID, _id);
        contentValue.put(_NAME, name);
        contentValue.put(SPECIALITY_NUMBER, count);

        database.insert(TABLE_NAME_SPECIALITY, null, contentValue);
    }

    public int update(long _id,String name, String count) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(_NAME, name);
        contentValue.put(SPECIALITY_NUMBER, count);;
        int i = database.update(TABLE_NAME_SPECIALITY, contentValue, _ID + " = " +_id, null);
        return i;
    }
    public int updateTwo(long _id, String count) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(SPECIALITY_NUMBER, count);;
        int i = database.update(TABLE_NAME_SPECIALITY, contentValue, _ID + " = " +_id, null);
        return i;
    }
	
    public void delete(long _id) {
        database.delete(TABLE_NAME_SPECIALITY, _ID + "=" + _id, null);
    }
	
    public int deleteAll(){
        return database.delete(TABLE_NAME_SPECIALITY,"1",null);
    }


    public ArrayList<Speciality> listspeciality() {
        String sql = "select * from " + TABLE_NAME_SPECIALITY;
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        ArrayList<Speciality> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                long id = Integer.parseInt(cursor.getString(0));
                String Name = cursor.getString(1);
                String count = cursor.getString(2);
                storeContacts.add(new Speciality(id, Name, count));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }


}
