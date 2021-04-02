package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PetDbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "shelter.db";
    public static final int DB_VERSION = 1;
    public static String make_table = "CREATE TABLE pets ( " + PetContract.PetEntry._ID +" INTEGER, "+ PetContract.PetEntry.COLUMN_NAME +" TEXT, "+ PetContract.PetEntry.COLUMN_BREED +" TEXT, "+ PetContract.PetEntry.COLUMN_GENDER +" INTEGER, "+ PetContract.PetEntry.COLUMN_WEIGHT +" INTEGER );";

    public PetDbHelper(Context context){
        super(context, DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(make_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
