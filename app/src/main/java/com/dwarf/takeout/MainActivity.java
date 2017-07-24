package com.dwarf.takeout;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.dwarf.takeout.ui.fragment.HomeFragment;
import com.dwarf.takeout.ui.fragment.InvestFragment;
import com.dwarf.takeout.ui.fragment.MeFragment;
import com.dwarf.takeout.ui.fragment.MoreFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mMainFLContainer;
    private LinearLayout mMainBottomSwitchContainer;
    private int mChildCount;
    //1创建 fragment 集合用来保存 tab 栏对应的 fragment 对象
    List<Fragment> fragments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainFLContainer = (FrameLayout) findViewById(R.id.main_fragment_container);
        mMainBottomSwitchContainer = (LinearLayout) findViewById(R.id.main_bottome_switcher_container);

        //2创建fragment保存到集合中
        fragments.add(new HomeFragment());
        fragments.add(new InvestFragment());
        fragments.add(new MeFragment());
        fragments.add(new MoreFragment());

        switchContainerLinstener.onClick(mMainBottomSwitchContainer.getChildAt(0));

        mChildCount = mMainBottomSwitchContainer.getChildCount();
        for (int i = 0; i < mChildCount; i++) {
            mMainBottomSwitchContainer.getChildAt(i).setOnClickListener(switchContainerLinstener);
        }
        //4初始化设置选中第一个tab
    }

    private View.OnClickListener switchContainerLinstener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = mMainBottomSwitchContainer.indexOfChild(v);
            updateSwitchUI(index);
            //更新对应的Fragment
            switchFragment(index);
        }
    };

    /**
     * 3使用 FragmentManager 开启 fragment 事务，切换 fragment
     */
    private void switchFragment(int index) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container,fragments.get(index))
                .commit();
    }

    private void updateSwitchUI(int index) {
        mChildCount = mMainBottomSwitchContainer.getChildCount();
        for (int i = 0; i < mChildCount; i++) {
            setEnable(mMainBottomSwitchContainer.getChildAt(i),index!=i);
        }
//        View childAt = mMainBottomSwitchContainer.getChildAt(index);
    }

    private void setEnable(View childAt, boolean b) {
        childAt.setEnabled(b);
        if (childAt instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) childAt).getChildCount(); i++) {
                setEnable(((ViewGroup) childAt).getChildAt(i),b);
            }
        }
    }
}
