package com.dwarf.takeout.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dwarf.takeout.MyApplication;
import com.dwarf.takeout.R;
import com.dwarf.takeout.dagger.component.fragment.DaggerHomeFragmentCommponet;
import com.dwarf.takeout.dagger.module.fragment.HomeFragmentModule;
import com.dwarf.takeout.presenter.fragment.HomeFragmentPresenter;
import com.dwarf.takeout.ui.adapter.HomeRecyclerViewAdapter;
import com.nineoldandroids.animation.ArgbEvaluator;

import javax.inject.Inject;



/**
 * Created by qurongzhen on 2017/7/21.
 */

public class HomeFragment extends BaseFragment {

    private SwipeRefreshLayout mSrlHome;
    private LinearLayout mLlTitleContainer;
    private RecyclerView mRvHome;
    private LinearLayout mLlTitleSearch;
    private TextView mHomeTvAddress;

    @Inject
    HomeFragmentPresenter presenter;
    private HomeRecyclerViewAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerHomeFragmentCommponet.builder().homeFragmentModule(new HomeFragmentModule(this)).build().in(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        mSrlHome = (SwipeRefreshLayout) view.findViewById(R.id.srl_home);
        mRvHome = (RecyclerView) view.findViewById(R.id.rv_home);
        mLlTitleContainer = (LinearLayout) view.findViewById(R.id.ll_title_container);
        mLlTitleSearch = (LinearLayout) view.findViewById(R.id.ll_title_search);
        mHomeTvAddress = (TextView) view.findViewById(R.id.home_tv_address);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new HomeRecyclerViewAdapter(MyApplication.getmContext());
        mRvHome.setAdapter(adapter);
        mRvHome.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));
        mRvHome.addOnScrollListener(listener);

        presenter.getData();
    }

    private int sumY=0;
    private float duration=150.0f;//在0-150之间去改变头部的透明度
    private ArgbEvaluator evaluator=new ArgbEvaluator();
    private RecyclerView.OnScrollListener listener=new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            sumY+=dy;
            // 滚动的总距离相对0-150之间有一个百分比，头部的透明度也是从初始值变动到不透明，通过距离的百分比，得到透明度对应的值
            // 如果小于0那么透明度为初始值，如果大于150为不透明状态

            int bgColor=0X553190E8;
            if(sumY<0){
                bgColor=0X553190E8;
            }else if(sumY>150){
                bgColor=0XFF3190E8;
            }else{
                bgColor = (int) evaluator.evaluate(sumY / duration, 0X553190E8, 0XFF3190E8);
            }

            mLlTitleContainer.setBackgroundColor(bgColor);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        presenter.getData();
    }

    public HomeRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public void failed(String message) {
        Toast.makeText(getContext(), "数据加载失败"+message, Toast.LENGTH_SHORT).show();
    }
}
