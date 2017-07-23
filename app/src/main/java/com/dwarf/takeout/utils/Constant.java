package com.dwarf.takeout.utils;


import com.dwarf.takeout.MyApplication;

/**
 * Created by Teacher on 2016/9/2.
 */
public  interface Constant {

//    String ip = MyApplication.ip;
    String ip = "192.168.125.28";
    //192.168.199.215
    //192.168.82.250
     String replace_img_url = ip;
    // http://localhost:8080/   TakeoutService    /login?username="itheima"&password="bj"

     String BASEURL = "http://" + ip + ":8080/";
    //    String BASEURL="http://192.168.199.215:8080/";
    // 登陆
     String LOGIN = "TakeoutService/login";
    // http://localhost:8080/TakeoutService/home
     String HOME = "TakeoutService/home";
    // http://localhost:8080/TakeoutService/goods?sellerId=1
    String GOODS = "TakeoutService/goods";
    //    http://localhost:8080/TakeoutService/address?userId=2163&&&&&&
     String ADDRESS = "TakeoutService/address";

    String ORDER = "TakeoutService/order";
    String PAY = "TakeoutService/pay";

    // 短信登陆的分类值
     int LOGIN_TYPE_SMS = 2;

     String LAT = "Lat";
     String LNG = "Lng";


    public static int EDIT_ADDRESS_REQUESTCODE = 0x100;
    public static int ADD_ADDRESS_REQUESTCODE = 0x200;
    public static int DELETE_ADDRESS_REQUESTCODE = 0x300;


}
