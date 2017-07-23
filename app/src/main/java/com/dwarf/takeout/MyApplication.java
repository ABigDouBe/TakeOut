package com.dwarf.takeout;

import android.app.Application;
import android.content.Context;

/**
 * Created by qurongzhen on 2017/7/21.
 */

public class MyApplication extends Application{

    private static MyApplication instance;
    private static Context mContext;
    public static String ip;

    public static MyApplication getInstance() {
        return instance;
    }
    public static Context getmContext() {
        return mContext;
    }
    // 用户是否登陆的标识，如果为-1表示没有登陆
    public int USREID = -1;

    @Override
    public void onCreate() {
        instance = this;
//        JPushInterface.init(this);
        mContext = getApplicationContext();

        super.onCreate();
    }
}
