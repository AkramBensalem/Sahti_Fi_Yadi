package com.gadg.sahtifiyadi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.gadg.sahtifiyadi.items.pharmacy;

import static com.gadg.sahtifiyadi.database.TablesColumnsNames.Pharmacy_columns.*;
import static com.gadg.sahtifiyadi.database.TablesColumnsNames.TABLE_NAME_PHARMACIE;


public class DBManagerPharmacy {
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManagerPharmacy(Context c) {
        context = c;
    }

    public DBManagerPharmacy open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public boolean CheckIsDataAlreadyInDBorNot(String nameFeild) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String Query = "Select * from " + TABLE_NAME_PHARMACIE + " where " + _ID_FIREBASE + " = '" + nameFeild +"' ";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public pharmacy getPharmacieFromID(int id) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String Query = "Select * from " + TABLE_NAME_PHARMACIE + " where " + _ID + " = "+id ;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()) {
            String Id_firebase = cursor.getString(1);
            String name = cursor.getString(2);
            String place = cursor.getString(3);
            int wilaya = cursor.getInt(4);
            int commune = cursor.getInt(5);
            String phone = cursor.getString(6);
            String time = cursor.getString(7);
            String description = cursor.getString(8);
            String imageUrl = cursor.getString(9);
            cursor.close();
            return new pharmacy(Id_firebase,name, place, wilaya,commune,phone,time,imageUrl,description);
        }
        cursor.close();
        return null;
    }

    public void insert(String _id,String name, String place, int wilaya , int commune,String phone, String time,String imageUrl,String description) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(_ID_FIREBASE, _id);
        contentValue.put(_NAME, name);
        contentValue.put(_PLACE, place);
        contentValue.put(_WILAYA, wilaya);
        contentValue.put(_COMMUNE, commune);
        contentValue.put(_PHONE, phone);
        contentValue.put(_TIME, time);
        contentValue.put(_IMAGE, imageUrl);
        contentValue.put(_DESCRIPTION,description);
        database.insert(TABLE_NAME_PHARMACIE, null, contentValue);
    }


    public int update(String _id, String name, String place, int wilaya , int commune,String phone,String time,String imageUrl,String description) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(_NAME, name);
        contentValue.put(_PLACE, place);
        contentValue.put(_WILAYA, wilaya);
        contentValue.put(_COMMUNE, commune);
        contentValue.put(_PHONE, phone);
        contentValue.put(_TIME, time);
        contentValue.put(_IMAGE, imageUrl);
        contentValue.put(_DESCRIPTION,description);
        int i = database.update(TABLE_NAME_PHARMACIE, contentValue, _ID_FIREBASE + " = " + "'"+_id+ "'", null);
        return i;
    }
    public void delete(long _id) {
        database.delete(TABLE_NAME_PHARMACIE, _ID + "=" + _id, null);
    }

    public void deleteByFireBaseId(String _id) {
        database.delete(TABLE_NAME_PHARMACIE, _ID_FIREBASE + " = " + "'"+_id+ "'", null);
    }

    public void deleteAll(){
        String sql = "select * from " + TABLE_NAME_PHARMACIE;
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                this.delete(id);
            }
            while (cursor.moveToNext());
        }
        cursor.close();

    }
}
