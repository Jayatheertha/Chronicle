package com.jaysmec.chronicle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jayat on 29-Sep-16.
 */

public class ChronicleDb extends SQLiteOpenHelper {
    public static final String DBNAAME = "Chronicledb";
    public static final int DBVERSION = 1;
    public ChronicleDb(Context context){
        super(context,DBNAAME,null,DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table Chronicledb (entry TEXT,tag TEXT,time TEXT,date TEXT,location TEXT,temp INTEGER,cid INTEGER,tim INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST ExampleDB");

    }
}
