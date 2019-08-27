package com.example.yunihafsari.fypversion3.sqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.yunihafsari.fypversion3.utils.Constants;

/**
 * Created by yunihafsari on 21/03/2017.
 */

public class DBAdapter {
    Context context;
    //SQLiteDatabase database;
    SQLiteDatabase database=null;
    DBHelper dbHelper;

    public DBAdapter(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    // open DB
    public DBAdapter openDB(){

        try{
            //database = dbHelper.getWritableDatabase();
            database = dbHelper.getWritableDatabase();
        }catch (SQLiteException e){
            e.printStackTrace();
        }

        return this;
    }

    // close DB
    public void closeDB(){

        try{
            dbHelper.close();
        }catch (SQLiteException e){
            e.printStackTrace();
        }
    }

    // insert data to DB
    public long add(String name, String email, String phone_number, String relation, String address, String twitter_username, String instagramm_username, Integer firebase ,byte[] image){

        try{
            ContentValues cv = new ContentValues();
            cv.put(Constants.NAME, name);
            cv.put(Constants.EMAIL, email);
            cv.put(Constants.PHONE_NUMBER, phone_number);
            cv.put(Constants.RELATION, relation);
            cv.put(Constants.ADDRESS, address );
            cv.put(Constants.TWITTER_USERNAME, twitter_username );
            cv.put(Constants.INSTAGRAM_USERNAME, instagramm_username );
            cv.put(Constants.FIREBASE, firebase);
            cv.put(Constants.IMAGE, image);

            //return database.insert(Constants.TB_NAME, Constants.ROW_ID, cv);
            return database.insert(Constants.TB_NAME, Constants.ROW_ID, cv);

        }catch (SQLiteException e){
            e.printStackTrace();
        }

        return 0;
    }


    // insert data to instagram table
    public long add_to_instagram_table(String id, String full_name, String username){
        try{
            ContentValues cv = new ContentValues();
            cv.put(Constants.INSTAGRAM_ID, id);
            cv.put(Constants.INSTAGRAM_FULL_NAME, full_name);
            cv.put(Constants.INSTAGRAM_USERNAMEE, username);

            //return database.insert(Constants.TB_NAME, Constants.ROW_ID, cv);
            return database.insert(Constants.TB_INSTAGRAM, Constants.INSTAGRAM_TABLE_ID, cv);

        }catch (SQLiteException e){
            e.printStackTrace();
        }

        return 0;
    }

    // get data
    public Cursor getAllFriends(){
        //String[] columns = {Constants.ROW_ID, Constants.NAME, Constants.EMAIL, Constants.PHONE_NUMBER,
        //                    Constants.RELATION, Constants.ADDRESS , Constants.TWITTER_USERNAME, Constants.INSTAGRAM_USERNAME, Constants.IMAGE};
        String[] columns = {Constants.ROW_ID, Constants.NAME, Constants.EMAIL, Constants.PHONE_NUMBER,
                            Constants.RELATION, Constants.ADDRESS, Constants.TWITTER_USERNAME, Constants.INSTAGRAM_USERNAME, Constants.FIREBASE ,Constants.IMAGE};
        //return database.query(Constants.TB_NAME, columns, null, null, null,null,null, null,null,null);
        //return database.query(Constants.TB_NAME, columns, null, null, null, null, null, null,null,null);
        return database.query(Constants.TB_NAME, columns, null, null, null, null, null, null);
    }


    // get data from instagram table
    public Cursor getAllInstagramFriends(){
        String[] columns = {Constants.INSTAGRAM_TABLE_ID, Constants.INSTAGRAM_ID, Constants.INSTAGRAM_FULL_NAME, Constants.INSTAGRAM_USERNAMEE};

        return database.query(Constants.TB_INSTAGRAM, columns, null, null, null, null, null, null);
    }

    // update
    public long UPDATE(int id, String name, String email, String phone_number, String relation,
                       String address, String twitter, String instagram){

        try{
            ContentValues cv = new ContentValues();
            cv.put(Constants.NAME, name);
            cv.put(Constants.EMAIL, email);
            cv.put(Constants.PHONE_NUMBER, phone_number);
            cv.put(Constants.RELATION, relation);
            cv.put(Constants.ADDRESS, address);
            cv.put(Constants.TWITTER_USERNAME, twitter);
            cv.put(Constants.INSTAGRAM_USERNAME, instagram);

            return database.update(Constants.TB_NAME, cv, Constants.ROW_ID+" =?",new String[]{String.valueOf(id)});
        }catch (SQLiteException e){
            e.printStackTrace();
        }

        return 0;
    }

    // delete
    public long delete(int id){

        try{
            return database.delete(Constants.TB_NAME, Constants.ROW_ID+" =?",new String[]{String.valueOf(id)});
        }catch (SQLiteException e){
            e.printStackTrace();
        }

        return 0;
    }
}
