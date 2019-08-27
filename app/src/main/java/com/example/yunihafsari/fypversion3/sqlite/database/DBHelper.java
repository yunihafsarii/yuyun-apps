package com.example.yunihafsari.fypversion3.sqlite.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.yunihafsari.fypversion3.utils.Constants;

/**
 * Created by yunihafsari on 21/03/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    // CREATE DATABASE
    public DBHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    //CREATE TABLE
    @Override
    public void onCreate(SQLiteDatabase db) {

        try{
            db.execSQL(Constants.CREATE_TB);
            db.execSQL(Constants.CREATE_INSTAGRAM_TB);
        }catch(SQLiteException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Constants.TB_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+Constants.TB_INSTAGRAM);
        onCreate(db);
    }
}
