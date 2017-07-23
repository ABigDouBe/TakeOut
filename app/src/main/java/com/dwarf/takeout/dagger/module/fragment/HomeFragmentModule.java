package com.dwarf.takeout.dagger.module.fragment;

import com.dwarf.takeout.presenter.fragment.HomeFragmentPresenter;
import com.dwarf.takeout.ui.fragment.HomeFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by qurongzhen on 2017/7/21.
 */

@Module
public class HomeFragmentModule {
    HomeFragment fragment;

    public HomeFragmentModule(HomeFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    public HomeFragmentPresenter provideHomeFragmentPresenter(){
        return new HomeFragmentPresenter(fragment);
    }
}
