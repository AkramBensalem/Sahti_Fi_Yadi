package com.gadg.sahtifiyadi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import com.gadg.sahtifiyadi.ui.don_de_sang.don_de_song;

import java.util.ArrayList;

import static com.gadg.sahtifiyadi.database.TablesColumnsNames.Doctor_columns._SPECIALITY;
import static com.gadg.sahtifiyadi.database.TablesColumnsNames.Donor_columns.*;
import static com.gadg.sahtifiyadi.database.TablesColumnsNames.TABLE_NAME_DOCTORS;
import static com.gadg.sahtifiyadi.database.TablesColumnsNames.TABLE_NAME_DONATEUR;

public class DBManagerDonateur {

    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;


    public DBManagerDonateur(Context c) {
        context = c;
    }

    public DBManagerDonateur open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public boolean CheckIsDataAlreadyInDBorNot(String nameFeild) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String Query = "Select * from " + TABLE_NAME_DONATEUR + " where " + _ID_FIREBASE + " = '" + nameFeild +"' ";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public void insert(String _id,String name, String place,String wilaya, String commune, String phone,String age,String grsanguin,String imageUrl) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(_ID_FIREBASE, _id);
        contentValue.put(AGE, age);
        contentValue.put(_NAME, name);
        contentValue.put(_PLACE, place);
        contentValue.put(_WILAYA, wilaya);
        contentValue.put(_COMMUNE, commune);
        contentValue.put(_PHONE, phone);
        contentValue.put(GRSANGUIN_DONATEUR, grsanguin);
        contentValue.put(_IMAGE, imageUrl);
        database.insert(TABLE_NAME_DONATEUR, null, contentValue);
    }


    public int update(String _id, String name, String place,String wilaya, String commune, String phone,String age,String grsanguin,String imageUrl) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(AGE, age);
        contentValue.put(_NAME, name);
        contentValue.put(_PLACE, place);
        contentValue.put(_WILAYA, wilaya);
        contentValue.put(_COMMUNE, commune);
        contentValue.put(_PHONE, phone);
        contentValue.put(GRSANGUIN_DONATEUR, grsanguin);
        contentValue.put(_IMAGE, imageUrl);

        int i = database.update(TABLE_NAME_DONATEUR, contentValue, _ID_FIREBASE + " = " + "'"+_id+ "'", null);
        return i;
    }

    public void delete(long _id) {
        database.delete(TABLE_NAME_DONATEUR, _ID+ "=" + _id, null);
    }


    public ArrayList<don_de_song> listdonateur() {
        String sql = "select * from " + TABLE_NAME_DONATEUR;
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        ArrayList<don_de_song> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String Id_firebase = cursor.getString(1);
                String name = cursor.getString(2);
                String place = cursor.getString(3);
                String wilaya = cursor.getString(4);
                String commune = cursor.getString(5);
                String phone = cursor.getString(6);
                String age=cursor.getString(7);
                String grsnaguin=cursor.getString(8);
                String imageUrl = cursor.getString(9);

                storeContacts.add(new don_de_song(Id_firebase,name, place,wilaya,commune, phone,age,grsnaguin,imageUrl));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }

    public ArrayList<don_de_song> listdonateur(String willaya, String Commune, String sanguin) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        final String[] donateurColumn = {
                _ID,
                _ID_FIREBASE,
                _NAME,
                _PLACE,
                _WILAYA,
                _COMMUNE,
                _PHONE,
                AGE,
                GRSANGUIN_DONATEUR,
                _IMAGE};


        String selection=null;
        String[] selectionArgs= null;
        if (willaya.equals("Wilaya")){
            selection = GRSANGUIN_DONATEUR +" = ?";
            selectionArgs = new String[]{sanguin};
        }else {
            if (Commune.equals("Commune")){
                selection = GRSANGUIN_DONATEUR + " = ? AND " + _PLACE + " LIKE ?";
                selectionArgs = new String[]{sanguin, "%" + willaya.toUpperCase()};

            }else {
                selection = GRSANGUIN_DONATEUR + " = ? AND " + _PLACE + " LIKE ?";
                selectionArgs = new String[]{sanguin, "%" + Commune + "%" + willaya.toUpperCase()};
            }
        }
        String OrderBy = _NAME ;
        Cursor cursor =db.query(TABLE_NAME_DONATEUR, donateurColumn,
                selection, selectionArgs, null, null, OrderBy);
        ArrayList<don_de_song> storeContacts = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String Id_firebase = cursor.getString(1);
                String name = cursor.getString(2);
                String place = cursor.getString(3);
                String wilaya = cursor.getString(4);
                String commune = cursor.getString(5);
                String phone = cursor.getString(6);
                String age=cursor.getString(7);
                String grsnaguin=cursor.getString(8);
                String imageUrl = cursor.getString(9);

                storeContacts.add(new don_de_song(Id_firebase,name, place,wilaya,commune, phone,age,grsnaguin,imageUrl));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }


	public int deleteAll(){
        return database.delete(TABLE_NAME_DONATEUR,"1",null);
    }
   
}
