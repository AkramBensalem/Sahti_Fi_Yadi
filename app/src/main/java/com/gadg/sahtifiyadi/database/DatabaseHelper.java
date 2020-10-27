package com.gadg.sahtifiyadi.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Information
    static final String DB_NAME = "SahtiFiYdi.db";

    // database version
    static final int DB_VERSION = 2;


    // Creating table query
    private static final String CREATE_TABLE_PHARMACIE = "create table " + TablesColumnsNames.TABLE_NAME_PHARMACIE + "("
            + TablesColumnsNames.Pharmacy_columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TablesColumnsNames.Pharmacy_columns._ID_FIREBASE + " TEXT UNIQUE NOT NULL, "
            + TablesColumnsNames.Pharmacy_columns._NAME + " TEXT NOT NULL, "
            + TablesColumnsNames.Pharmacy_columns._PLACE + " TEXT, "
            + TablesColumnsNames.Pharmacy_columns._WILAYA + " INTEGER, "
            + TablesColumnsNames.Pharmacy_columns._COMMUNE + " INTEGER, "
            + TablesColumnsNames.Pharmacy_columns._PHONE+ " TEXT, "
            + TablesColumnsNames.Pharmacy_columns._TIME + " TEXT, "
            + TablesColumnsNames.Pharmacy_columns._DESCRIPTION + " TEXT, "
            + TablesColumnsNames.Pharmacy_columns._IMAGE +" TEXT );";

    public static final String INDEX_PHAR = TablesColumnsNames.TABLE_NAME_PHARMACIE + "_indexphar";
    public static final String SQL_CREATE_INDEX_PHAR =
            "CREATE INDEX " + INDEX_PHAR + " ON " + TablesColumnsNames.TABLE_NAME_PHARMACIE +
                    "(" + TablesColumnsNames.Pharmacy_columns._ID_FIREBASE + ")";




    private static final String CREATE_TABLE_HOSPITAL = "create table " + TablesColumnsNames.TABLE_NAME_HOSPITAL + "("
            + TablesColumnsNames.Etablissement_columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TablesColumnsNames.Etablissement_columns._ID_FIREBASE+" TEXT  UNIQUE NOT NULL, "
            + TablesColumnsNames.Etablissement_columns._NAME + " TEXT NOT NULL, "
            + TablesColumnsNames.Etablissement_columns._DESCRIPTION + " TEXT, "
            + TablesColumnsNames.Etablissement_columns._PLACE + " TEXT, "
            + TablesColumnsNames.Etablissement_columns._WILAYA + " INTEGER, "
            + TablesColumnsNames.Etablissement_columns._COMMUNE + " INTEGER, "
            + TablesColumnsNames.Etablissement_columns._TYPE + " INTEGER, "
            + TablesColumnsNames.Etablissement_columns._PHONE + " TEXT UNIQUE, "
            + TablesColumnsNames.Etablissement_columns._SERVICE + " TEXT, "
            + TablesColumnsNames.Etablissement_columns._IMAGE+" TEXT );";

    public static final String INDEX_HOS = TablesColumnsNames.TABLE_NAME_HOSPITAL + "_indexhos";
    public static final String SQL_CREATE_INDEX_HOPITAL =
            "CREATE INDEX " + INDEX_HOS + " ON " + TablesColumnsNames.TABLE_NAME_HOSPITAL +
                    "(" + TablesColumnsNames.Etablissement_columns._ID_FIREBASE + ")";



    private static final String CREATE_TABLE_LABORATOIR = "create table " + TablesColumnsNames.TABLE_NAME_LABORATOIR + "("
            + TablesColumnsNames.Etablissement_columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TablesColumnsNames.Etablissement_columns._ID_FIREBASE+" TEXT  UNIQUE NOT NULL, "
            + TablesColumnsNames.Etablissement_columns._NAME + " TEXT NOT NULL, "
            + TablesColumnsNames.Etablissement_columns._DESCRIPTION + " TEXT, "
            + TablesColumnsNames.Etablissement_columns._PLACE + " TEXT, "
            + TablesColumnsNames.Etablissement_columns._WILAYA + " INTEGER, "
            + TablesColumnsNames.Etablissement_columns._COMMUNE + " INTEGER, "
            + TablesColumnsNames.Etablissement_columns._TYPE + " INTEGER, "
            + TablesColumnsNames.Etablissement_columns._PHONE + " TEXT UNIQUE, "
            + TablesColumnsNames.Etablissement_columns._SERVICE + " TEXT, "
            + TablesColumnsNames.Etablissement_columns._IMAGE+" TEXT );";


    public static final String INDEX_LAB = TablesColumnsNames.TABLE_NAME_HOSPITAL + "_indexlab";
    public static final String SQL_CREATE_INDEX_LABO =
            "CREATE INDEX " + INDEX_LAB + " ON " + TablesColumnsNames.TABLE_NAME_LABORATOIR +
                    "(" + TablesColumnsNames.Etablissement_columns._ID_FIREBASE + ")";


    private static final String CREATE_TABLE_DOCTORS = "create table " + TablesColumnsNames.TABLE_NAME_DOCTORS + "("
            + TablesColumnsNames.Doctor_columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TablesColumnsNames.Doctor_columns._ID_FIREBASE + " TEXT UNIQUE NOT NULL, "
            + TablesColumnsNames.Doctor_columns._NAME + " TEXT NOT NULL, "
            + TablesColumnsNames.Doctor_columns._PLACE + " TEXT, "
            + TablesColumnsNames.Doctor_columns._WILAYA + " INTEGER, "
            + TablesColumnsNames.Doctor_columns._COMMUNE + " INTEGER, "
            + TablesColumnsNames.Doctor_columns._PHONE+ " TEXT, "
            + TablesColumnsNames.Doctor_columns._SPECIALITY+" TEXT NOT NULL, "
            + TablesColumnsNames.Doctor_columns._TYPE +" TEXT, "
            + TablesColumnsNames.Doctor_columns._SERVICE+" TEXT, "
            + TablesColumnsNames.Doctor_columns._TIME+" TEXT, "
            + TablesColumnsNames.Doctor_columns._IMAGE + " TEXT );";

    public static final String INDEX_DOC = TablesColumnsNames.TABLE_NAME_HOSPITAL + "_indexdoc";
    public static final String SQL_CREATE_INDEX_DOCTOR =
            "CREATE INDEX " + INDEX_DOC + " ON " + TablesColumnsNames.TABLE_NAME_DOCTORS +
                    "(" + TablesColumnsNames.Doctor_columns._ID_FIREBASE + ")";


    private static final String CREATE_TABLE_MESSAGES ="create table "+TablesColumnsNames.TABLE_NAME_MESSAGES+"("
     + TablesColumnsNames.Message_columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
     + TablesColumnsNames.Message_columns._ID_FIREBASE+ " TEXT UNIQUE NOT NULL, "
     + TablesColumnsNames.Message_columns._NAME+ " TEXT NOT NULL, "
     + TablesColumnsNames.Message_columns.RECENT_MESSAGE+ " TEXT, "
     + TablesColumnsNames.Message_columns.FULL_MESSAGE+ " TEXT ,"
     + TablesColumnsNames.Message_columns.MESSAGE_RECENT_DATE+ " TEXT, "
     + TablesColumnsNames.Message_columns.IS_READ + " TEXT ,"
     + TablesColumnsNames.Message_columns._IMAGE+" TEXT);";
  
    public static final String INDEX_MES = TablesColumnsNames.TABLE_NAME_MESSAGES + "_indexmes";
    public static final String SQL_CREATE_INDEX_MESSAGE =
            "CREATE INDEX " + INDEX_MES + " ON " + TablesColumnsNames.TABLE_NAME_MESSAGES +
                    "(" + TablesColumnsNames.Message_columns._ID_FIREBASE + ")";



    private static final String CREATE_TABLE_DONATEUR = "create table " + TablesColumnsNames.TABLE_NAME_DONATEUR + "("
            + TablesColumnsNames.Donor_columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +TablesColumnsNames.Donor_columns._ID_FIREBASE + " TEXT UNIQUE NOT NULL, "
            + TablesColumnsNames.Donor_columns._NAME + " TEXT NOT NULL, "
            + TablesColumnsNames.Donor_columns._PLACE + " TEXT, "
            + TablesColumnsNames.Donor_columns._WILAYA + " INTEGER, "
            + TablesColumnsNames.Donor_columns._COMMUNE + " INTEGER, "
            + TablesColumnsNames.Donor_columns._PHONE+ " TEXT, "
            + TablesColumnsNames.Donor_columns.AGE + " TEXT NOT NULL, "
            + TablesColumnsNames.Donor_columns.GRSANGUIN_DONATEUR + " TEXT, "
            + TablesColumnsNames.Donor_columns._IMAGE+" TEXT );";

    public static final String INDEX_DON = TablesColumnsNames.TABLE_NAME_DONATEUR + "_indexdon";
    public static final String SQL_CREATE_INDEX_DONATION =
            "CREATE INDEX " + INDEX_DON + " ON " + TablesColumnsNames.TABLE_NAME_DONATEUR +
                    "(" + TablesColumnsNames.Donor_columns._ID_FIREBASE + ")";

 private static final String CREATE_TABLE_SPECIALITY = "create table " + TablesColumnsNames.TABLE_NAME_SPECIALITY + "("
            + TablesColumnsNames.Speciality_columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TablesColumnsNames.Speciality_columns._NAME + " TEXT UNIQUE NOT NULL, "
            + TablesColumnsNames.Speciality_columns.SPECIALITY_NUMBER+" TEXT );";

    public static final String INDEX_SPEC = TablesColumnsNames.TABLE_NAME_SPECIALITY + "_indexspec";
    public static final String SQL_CREATE_INDEX_SPEC =
            "CREATE INDEX " + INDEX_SPEC + " ON " + TablesColumnsNames.TABLE_NAME_SPECIALITY +
                    "(" + TablesColumnsNames.Speciality_columns._NAME + ")";




    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PHARMACIE);
        db.execSQL(CREATE_TABLE_HOSPITAL);
        db.execSQL(CREATE_TABLE_DOCTORS);
        db.execSQL(CREATE_TABLE_SPECIALITY);
        db.execSQL(CREATE_TABLE_LABORATOIR);
        db.execSQL(CREATE_TABLE_MESSAGES);
        db.execSQL(CREATE_TABLE_DONATEUR);

        db.execSQL(SQL_CREATE_INDEX_DOCTOR);
        db.execSQL(SQL_CREATE_INDEX_PHAR);
        db.execSQL(SQL_CREATE_INDEX_HOPITAL);
        db.execSQL(SQL_CREATE_INDEX_LABO);
        db.execSQL(SQL_CREATE_INDEX_SPEC);
        db.execSQL(SQL_CREATE_INDEX_MESSAGE);
        db.execSQL(SQL_CREATE_INDEX_DONATION);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TablesColumnsNames.TABLE_NAME_PHARMACIE);
        db.execSQL("DROP TABLE IF EXISTS " + TablesColumnsNames.TABLE_NAME_HOSPITAL);
        db.execSQL("DROP TABLE IF EXISTS " +TablesColumnsNames.TABLE_NAME_DOCTORS);
        db.execSQL("DROP TABLE IF EXISTS " +TablesColumnsNames.TABLE_NAME_SPECIALITY);
        db.execSQL("DROP TABLE IF EXISTS " +TablesColumnsNames.TABLE_NAME_LABORATOIR);
        db.execSQL("DROP TABLE IF EXISTS " +TablesColumnsNames.TABLE_NAME_MESSAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TablesColumnsNames.TABLE_NAME_DONATEUR);
        onCreate(db);
    }

}
