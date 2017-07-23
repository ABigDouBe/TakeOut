package com.dwarf.takeout.model.net.api;

import com.dwarf.takeout.model.bean.ResponseInfo;
import com.dwarf.takeout.utils.Constant;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by qurongzhen on 2017/7/21.
 */

public interface RequestAPI {
    @GET(Constant.HOME)
    Call<ResponseInfo> home();
}
