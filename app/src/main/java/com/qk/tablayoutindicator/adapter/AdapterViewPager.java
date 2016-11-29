package com.qk.tablayoutindicator.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.qk.tablayoutindicator.fragment.TabFragment;
import com.qk.tablayoutindicator.model.ModelTab;

import java.util.List;

/**
 * Created by qk on 2016/11/26.
 */

public class AdapterViewPager extends FragmentPagerAdapter {
    private List<ModelTab> data;
    public AdapterViewPager(FragmentManager fm, List<ModelTab> data) {
        super(fm);
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        ModelTab info = data.get(position);
        return new TabFragment(info.key);
    }

    @Override
    public int getCount() {
        return data.size();
    }

}
