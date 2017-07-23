package com.dwarf.takeout.model.bean;

/**
 * 服务器回复数据
 */

public class ResponseInfo {
    /**
     * {
         "code": "0",
         "data": "{……}"
      }

     */

    public String code;
    public String data;

    public String getCode() {
        return code;
    }

    public String getData() {
        return data;
    }
}
