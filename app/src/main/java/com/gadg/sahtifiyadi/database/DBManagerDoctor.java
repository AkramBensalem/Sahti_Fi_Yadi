package com.gadg.sahtifiyadi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gadg.sahtifiyadi.items.Doctors;

import static com.gadg.sahtifiyadi.database.TablesColumnsNames.Doctor_columns.*;
import static com.gadg.sahtifiyadi.database.TablesColumnsNames.TABLE_NAME_DOCTORS;


public class DBManagerDoctor {
     private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManagerDoctor(Context c) {
        context = c;
    }

    public DBManagerDoctor open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public boolean CheckIsDataAlreadyInDBorNot(String nameFeild) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String Query = "Select * from " + TABLE_NAME_DOCTORS + " where " + _ID_FIREBASE + " = '" + nameFeild +"' ";
            Cursor cursor = db.rawQuery(Query, null);
            if(cursor.moveToFirst()) {
                cursor.close();
                return true;
            }
            cursor.close();
        return false;
    }

    public Doctors getDoctorFromID(int id) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String Query = "Select * from " + TABLE_NAME_DOCTORS + " where " + _ID + " = "+id ;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()) {

            String Id_firebase = cursor.getString(1);
            String name = cursor.getString(2);
            String place = cursor.getString(3);
            String wilaya =  cursor.getString(4);
            String commune = cursor.getString(5);
            String phone = cursor.getString(6);
            String specialite = cursor.getString(7);
            String type = cursor.getString(8);
            String service = cursor.getString(9);
            String time = cursor.getString(10);
            String imageUrl = cursor.getString(11);

            cursor.close();
            return new Doctors(Id_firebase,name, place,wilaya,commune,phone,specialite ,type,service,time,imageUrl);
        }
        cursor.close();
        return null;
    }

// DOCTOR_TYPE +DOCTOR_SERVICE +DOCTOR_TIME

    public void insert(String _id,String name, String place,String wilaya , String commune,String phone, String spec,String type,String service,String time,String imageUrl) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(_ID_FIREBASE, _id);
        contentValue.put(_NAME, name);
        contentValue.put(_PLACE, place);
        contentValue.put(_WILAYA, wilaya);
        contentValue.put(_COMMUNE, commune);
        contentValue.put(_TYPE, type);
        contentValue.put(_SERVICE, service);
        contentValue.put(_TIME, time);
        contentValue.put(_SPECIALITY, spec);
        contentValue.put(_PHONE, phone);
        contentValue.put(_IMAGE, imageUrl);
try {
    database.insert(TABLE_NAME_DOCTORS, null, contentValue);

}catch (Exception e){
    Log.d("testdb","error "+e.getMessage()) ;}
    }

    public int update(String _id,String name, String place, String wilaya, String commune,String phone, String spec,String type,String service,String time,String imageUrl) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(_NAME, name);
        contentValue.put(_PLACE, place);
        contentValue.put(_WILAYA, wilaya);
        contentValue.put(_COMMUNE, commune);
        contentValue.put(_TYPE, type);
        contentValue.put(_SERVICE, service);
        contentValue.put(_TIME, time);
        contentValue.put(_SPECIALITY, spec);
        contentValue.put(_PHONE, phone);
        contentValue.put(_IMAGE, imageUrl);

        int i = database.update(TABLE_NAME_DOCTORS, contentValue, _ID_FIREBASE + " = " + "'"+_id+ "'", null);
        return i;
    }
    public void delete(long _id) {
        database.delete(TABLE_NAME_DOCTORS, _ID + "=" + _id, null);
    }

    public void deleteByFireBaseId(String _id) {
        database.delete(TABLE_NAME_DOCTORS, _ID_FIREBASE + " = '" + _id +"'", null);
    }


    public int deleteAll(){
        return database.delete(TABLE_NAME_DOCTORS,"1",null);
    }

}
