package com.gadg.sahtifiyadi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.gadg.sahtifiyadi.items.Etablissement;

import static com.gadg.sahtifiyadi.database.TablesColumnsNames.Etablissement_columns.*;


public class DBManagerEtablissement {
    private DatabaseHelper dbHelper;

    private Context context;
    private String mTableName;
    private SQLiteDatabase database;

    public DBManagerEtablissement(Context c, String table) {
        context = c;
        this.mTableName = table;

    }

    public DBManagerEtablissement open(String table) throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        this.mTableName = table;
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public boolean CheckIsDataAlreadyInDBorNot(String nameFeild) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String Query = "Select * from " + this.mTableName + " where " + _ID_FIREBASE + " = '" + nameFeild +"' ";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public Etablissement getHopitalFromID(int id) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String Query = "Select * from " + this.mTableName + " where " + _ID + " = "+id ;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()) {
            String Id_firebase = cursor.getString(1);
            String name = cursor.getString(2);
            String description = cursor.getString(3);
            String place = cursor.getString(4);
            String wilaya = cursor.getString(5);
            String commune =  cursor.getString(6);
            int type = cursor.getInt(7);
            String phone = cursor.getString(8);
            String service = cursor.getString(9);
            String imageUrl = cursor.getString(10);
            cursor.close();
            return new Etablissement(Id_firebase,name, description,place,wilaya,commune,type ,phone,service,imageUrl);
        }
        cursor.close();
        return null;
    }

    public void insert(String _id,String name,String description, String place,String wilaya , String commune,
                       int type,String phone,String service,String imageUrl) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(_ID_FIREBASE, _id);
        contentValue.put(_NAME, name);
        contentValue.put(_DESCRIPTION, description);
        contentValue.put(_PLACE, place);
        contentValue.put(_WILAYA, wilaya);
        contentValue.put(_COMMUNE, commune);
        contentValue.put(_TYPE, type);
        contentValue.put(_PHONE, phone);
        contentValue.put(_SERVICE, service);
        contentValue.put(_IMAGE, imageUrl);
        database.insert(this.mTableName, null, contentValue);
    }

    public int update(String _id, String name,String description ,String place,String wilaya , String commune,
                      int type,String phone,String service,String imageUrl) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(_NAME, name);
        contentValue.put(_DESCRIPTION, description);
        contentValue.put(_PLACE, place);
        contentValue.put(_WILAYA, wilaya);
        contentValue.put(_COMMUNE, commune);
        contentValue.put(_TYPE, type);
        contentValue.put(_PHONE, phone);
        contentValue.put(_SERVICE, service);
        contentValue.put(_IMAGE, imageUrl);

        int i = database.update(this.mTableName, contentValue, _ID_FIREBASE + " = " + "'"+_id+ "'", null);
        return i;
    }


    public void delete(long _id) {
        database.delete(this.mTableName, _ID+ "=" + _id, null);
    }
    public void deleteByFireBaseId(String _id) {
        database.delete(this.mTableName, _ID_FIREBASE + " = '" + _id +"'", null);
    }
    public int deleteAll(){
        return database.delete(this.mTableName,"1",null);
    }


}