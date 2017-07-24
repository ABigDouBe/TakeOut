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
    private boolean mIsSuccessed = false;

    public HomeFragmentPresenter(HomeFragment fragment) {
        this.fragment = fragment;
    }

    public void getData(){
        Call<ResponseInfo> home = mRequestAPI.home();
        home.enqueue(new CallbackAdapter());
    }

    @Override
    protected void failed(String message) {
        fragment.failed(message);
    }

    @Override
    protected void successed(String message) {
        fragment.successed(message);
    }


    @Override
    protected void parserData(String data) {
        // 解析数据：data
        Gson gson=new Gson();
        HomeInfo info = gson.fromJson(data, HomeInfo.class);
        fragment.getAdapter().setData(info);
    }
}
