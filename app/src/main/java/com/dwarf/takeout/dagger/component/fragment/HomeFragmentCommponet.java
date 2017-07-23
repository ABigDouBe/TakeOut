package com.dwarf.takeout.dagger.component.fragment;



import com.dwarf.takeout.dagger.module.fragment.HomeFragmentModule;
import com.dwarf.takeout.ui.fragment.HomeFragment;

import dagger.Component;

/**
 *
 */
@Component(modules = HomeFragmentModule.class)
public interface HomeFragmentCommponet {
    void in(HomeFragment fragment);
}
