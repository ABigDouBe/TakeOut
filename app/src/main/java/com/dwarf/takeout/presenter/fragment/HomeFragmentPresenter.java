package com.dwarf.takeout.presenter.fragment;

import com.dwarf.takeout.model.bean.HomeInfo;
import com.dwarf.takeout.model.bean.ResponseInfo;
import com.dwarf.takeout.presenter.base.BasePresenter;
import com.dwarf.takeout.ui.fragment.HomeFragment;
import com.dwarf.takeout.utils.ErrorInfo;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by qurongzhen on 2017/7/21.
 */

public class HomeFragmentPresenter extends BasePresenter {
    HomeFragment fragment;

    public HomeFragmentPresenter(HomeFragment fragment) {
        this.fragment = fragment;
    }

    public void getData(){
        Call<ResponseInfo> home = mRequestAPI.home();
        home.enqueue(new Callback<ResponseInfo>() {
            @Override
            public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
                // 处理回复
                if (response != null && response.isSuccessful()) {
                    ResponseInfo info = response.body();
                    if("0".equals(info.code)){
                        // 服务器端处理成功，并返回目标数据
                        parserData(info.data);
                    }else{
                        // 服务器端处理成功，返回错误提示，该信息需要展示给用户
                        // 依据code值获取到失败的数据
                        String msg = ErrorInfo.INFO.get(info.code);
                        failed(msg);
                    }

                } else {
                    // 联网过程中的异常

                    failed("shibai");
                }

            }

            @Override
            public void onFailure(Call<ResponseInfo> call, Throwable t) {
                //网络获取异常
                failed("yichang");
            }
        });
    }

    @Override
    protected void failed(String message) {
        fragment.failed(message);
    }
    @Override
    protected void parserData(String data) {
        // 解析数据：data
        Gson gson=new Gson();
        HomeInfo info = gson.fromJson(data, HomeInfo.class);
        fragment.getAdapter().setData(info);

    }
}
