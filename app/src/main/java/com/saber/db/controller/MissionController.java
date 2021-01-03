package com.saber.db.controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.saber.db.MySQLiteOpenHelper;
import com.saber.db.entity.Mission;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MissionController {

    public static void createTable(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE mission ("
                + "id varchar(16) primary key,"
                + "description varchar(128),"
                + "type varchar(4),"
                + "start_time varchar(16),"
                + "end_time carchar(16),"
                + "father varchar(16),"
                + "score varchar(4))");
    }

    @SuppressLint("SimpleDataFormat")
    public static String addMission(String description,
                                    String type,
                                    String endTime,
                                    String father,
                                    String score,
                                    MySQLiteOpenHelper mySQLiteOpenHelper){
        SQLiteDatabase sqLiteDatabase = mySQLiteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String id = System.currentTimeMillis() + "";
        String simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        values.put("id",id);
        values.put("description",description);
        values.put("type",type);
        values.put("start_time",simpleDateFormat);
        values.put("end_time",endTime);
        values.put("father",father);
        values.put("score",score);
        sqLiteDatabase.insert("mission", null, values);
        return id;
    }

    public static boolean deleteMission(String id, MySQLiteOpenHelper mySQLiteOpenHelper){
        SQLiteDatabase sqLiteDatabase = mySQLiteOpenHelper.getWritableDatabase();
        sqLiteDatabase.delete("mission","id=?",new String[]{id});
        return true;
    }

    public static boolean updateMission(String id,String[] columns,String[] values,MySQLiteOpenHelper mySQLiteOpenHelper){
        SQLiteDatabase sqLiteDatabase = mySQLiteOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for(int i=0;i<columns.length;i++){
            contentValues.put(columns[i],values[i]);
        }
        sqLiteDatabase.update("mission",contentValues,"id=?",new String[]{id});
        return true;
    }


    public static List<Mission> getMissionList(String queryStr, String[] queryValues, MySQLiteOpenHelper mySQLiteOpenHelper){
        SQLiteDatabase sqLiteDatabase = mySQLiteOpenHelper.getWritableDatabase();
        Cursor cursor = null;
        if(queryStr == null){
            cursor = sqLiteDatabase.query("mission",null,null,null,null,null,null);
        }else{
            cursor = sqLiteDatabase.query("mission",null,queryStr,queryValues,null,null,null);
        }
        List<Mission> missionList = new ArrayList<>();
        while (cursor.moveToNext()){
            missionList.add(new Mission(
                    cursor.getString(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("description")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getString(cursor.getColumnIndex("start_time")),
                    cursor.getString(cursor.getColumnIndex("end_time")),
                    cursor.getString(cursor.getColumnIndex("father")),
                    cursor.getString(cursor.getColumnIndex("score"))));

        }
        return missionList;
    }
}
