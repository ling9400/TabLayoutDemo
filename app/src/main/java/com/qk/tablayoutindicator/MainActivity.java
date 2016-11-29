package com.qk.tablayoutindicator;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qk.tablayoutindicator.adapter.AdapterRecycler;
import com.qk.tablayoutindicator.adapter.AdapterViewPager;
import com.qk.tablayoutindicator.model.ModelTab;

import java.util.ArrayList;
import java.util.List;

/***
 * 用recyclerView和ViewPager实现TabLayout效果
 * 讲解：其实水平的recyclerView和tabLayout是一样的效果
 * 本例只是楼主纯粹没事做换种写法写的，没有tabLayout那么复杂而已
 * 用recyclerView和ViewPager实现TabLayout效果的网易新闻Demo
 */
public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ViewPager viewPager;
    private List<ModelTab> mDatas;
    private AdapterRecycler adapterRecycler;
    private FullyLinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        bindData();
        setListener();
    }

    private void setListener() {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //将recyclerView和viewPage连接起来
                adapterRecycler.setCurrentSelect(position);
                recyclerView.smoothScrollToPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void bindData() {
        mDatas = new ArrayList<>();
        mDatas.add(new ModelTab("top", "头条"));
        mDatas.add(new ModelTab("guonei", "国内"));
        mDatas.add(new ModelTab("shehui", "社会"));
        mDatas.add(new ModelTab("guoji", "国际"));
        mDatas.add(new ModelTab("yule", "娱乐"));
        mDatas.add(new ModelTab("tiyu", "体育"));
        mDatas.add(new ModelTab("junshi", "军事"));
        mDatas.add(new ModelTab("keji", "科技"));
        mDatas.add(new ModelTab("caijing", "财经"));
        mDatas.add(new ModelTab("shishang", "时尚"));

        adapterRecycler = new AdapterRecycler(MainActivity.this, mDatas,
                new AdapterRecycler.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        clearSelect();
                        ModelTab info = mDatas.get(position);
                        info.isSelect = true;
                        adapterRecycler.notifyDataSetChanged();
                        viewPager.setCurrentItem(position);
                    }
                });
        recyclerView.setAdapter(adapterRecycler);
        adapterRecycler.setCurrentSelect(0);
        recyclerView.smoothScrollToPosition(0);
        recyclerView.scrollBy(0,0);

        AdapterViewPager adapterCircleViewPager =
                new AdapterViewPager(getSupportFragmentManager(), mDatas);
        viewPager.setAdapter(adapterCircleViewPager);
        viewPager.setCurrentItem(0);
    }

    private void clearSelect() {
        for(ModelTab info : mDatas){
            info.isSelect = false;
        }
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        linearLayoutManager = new FullyLinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
