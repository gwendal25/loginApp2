package com.example.loginapp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Date;

public class DBCashHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "CashLibrary.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_GAIN = "gain";
    private static final String TABLE_DEPENSE = "depense";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_VALUE = "valeur";
    private static final String COLUMN_CATEGORY = "categorie";
    private static final String COLUMN_DESCRIPTION  = "description";

    public DBCashHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_GAIN +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " date, " +
                COLUMN_VALUE + " INTEGER, " +
                COLUMN_CATEGORY + " VARCHAR(255), " +
                COLUMN_DESCRIPTION + " VARCHAR(255));";
        db.execSQL(query);

        String queryDepense = "CREATE TABLE " + TABLE_DEPENSE +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " date, " +
                COLUMN_VALUE + " INTEGER, " +
                COLUMN_CATEGORY + " VARCHAR(255), " +
                COLUMN_DESCRIPTION + " VARCHAR(255));";
        db.execSQL(queryDepense);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEPENSE);
        onCreate(db);
    }

    public void addDepense(String currentTime, int value, String categorie, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_DATE, currentTime);
        cv.put(COLUMN_VALUE, value);
        cv.put(COLUMN_CATEGORY, categorie);
        cv.put(COLUMN_DESCRIPTION, description);

        long result = db.insert(TABLE_DEPENSE, null, cv);
        if(result == -1){
            Toast.makeText(context, "insert to table depense failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "insert to table depense success", Toast.LENGTH_SHORT).show();
        }
    }

    public void addGain(String currentTime, int value, String categorie, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_DATE, currentTime);
        cv.put(COLUMN_VALUE, value);
        cv.put(COLUMN_CATEGORY, categorie);
        cv.put(COLUMN_DESCRIPTION, description);

        long result = db.insert(TABLE_GAIN, null, cv);
        if(result == -1){
            Toast.makeText(context, "insert to table gain failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "insert to table gain success", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllDataDepense(){
        String query = "SELECT * FROM " + TABLE_DEPENSE;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readAllDataGain(){
        String query = "SELECT * FROM " + TABLE_GAIN;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
