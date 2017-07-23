package com.dwarf.takeout.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Created by qurongzhen on 2017/7/21.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper{
    private static final String DATABASENAME = "itheima.db";
    private static final int DATABASEVERSION = 1;
    private static DBHelper instance;

    public DBHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    public static DBHelper getInstance(Context context) {
        if (instance == null) { //双重检查锁定，保证线程安全
            synchronized (DBHelper.class) {
                if (instance == null) {
                    instance =new DBHelper(context);
                }
            }
        }
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
