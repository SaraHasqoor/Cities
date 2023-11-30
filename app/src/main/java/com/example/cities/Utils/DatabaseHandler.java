package com.example.cities.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cities.Model.CitiesModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "citiesListDatabase";
    private static final String cities_TABLE = "cities";
    private static final String ID = "id";
    private static final String place = "place";
    private static final String STATUS = "status";
    private static final String CREATE_cities_TABLE = "CREATE TABLE " + cities_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + place + " TEXT, "
            + STATUS + " INTEGER)";

    private SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_cities_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + cities_TABLE);
        // Create tables again
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertplace(CitiesModel city) {
        ContentValues cv = new ContentValues();
        cv.put(place, city.getplace());
        cv.put(STATUS, 0);
        db.insert(cities_TABLE, null, cv);
    }

    @SuppressLint("Range")
    public List<CitiesModel> getAllPlaces() {
        List<CitiesModel> cityList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try {
            cur = db.query(cities_TABLE, null, null, null, null, null, null, null);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        CitiesModel city = new CitiesModel();
                        city.setId(cur.getInt(cur.getColumnIndex(ID)));
                        city.setPlace(cur.getString(cur.getColumnIndex(place)));
                        city.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                        cityList.add(city);
                    } while (cur.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            if (cur != null) {
                cur.close();
            }
        }
        return cityList;
    }

    public void updateStatus(int id, int status) {
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(cities_TABLE, cv, ID + "= ?", new String[]{String.valueOf(id)});
    }

    public void updatePlace(int id, String newPlace) {
        ContentValues cv = new ContentValues();
        cv.put(place, newPlace); // Use a different name for the parameter
        db.update(cities_TABLE, cv, ID + "= ?", new String[]{String.valueOf(id)});
    }

    public void deleteplace(int id) {
        db.delete(cities_TABLE, ID + "= ?", new String[]{String.valueOf(id)});
    }
}
