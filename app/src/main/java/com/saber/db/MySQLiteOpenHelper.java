package com.saber.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public MySQLiteOpenHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
    }

    public MySQLiteOpenHelper(Context context){
        super(context,"my.db",null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE mission ("
                + "id varchar(16) primary key,"
                + "description varchar(128),"
                + "type varchar(4),"
                + "start_time varchar(16),"
                + "end_time carchar(16),"
                + "father varchar(16),"
                + "score varchar(4),"
                + "strikethrough varchar(4))");

        db.execSQL("INSERT INTO mission(id,description,type,start_time,end_time,father,score,strikethrough) VALUES (\"00000001\",\"测试\",\"1\",\"2020-10-1 10:00\",\"2020-10-1 11:00\",\"0\",\"0\",\"0\")");
        db.execSQL("INSERT INTO mission(id,description,type,start_time,end_time,father,score,strikethrough) VALUES (\"00000002\",\"测试2\",\"2\",\"2020-10-1 10:00\",\"2020-10-1 11:00\",\"0\",\"0\",\"0\")");
        db.execSQL("INSERT INTO mission(id,description,type,start_time,end_time,father,score,strikethrough) VALUES (\"00000003\",\"测试3\",\"3\",\"2020-10-1 10:00\",\"2020-10-1 11:00\",\"0\",\"0\",\"0\")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
