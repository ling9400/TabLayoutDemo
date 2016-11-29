package com.qk.tablayoutindicator.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qk.tablayoutindicator.ActivityBrowser;
import com.qk.tablayoutindicator.R;
import com.qk.tablayoutindicator.adapter.TabAdapter;
import com.qk.tablayoutindicator.http.BaseCallBack;
import com.qk.tablayoutindicator.http.OkHttpHelper;
import com.qk.tablayoutindicator.model.ModelNews;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qk on 2016/11/10.
 */

public class TabFragment extends BaseFragment {
    private String tag;
    private View tabView;
    private RecyclerView recyclerView;
    private ListView lvTab;
    private TabAdapter tabAdapter;
    private String url = "http://v.juhe.cn/toutiao/index";
    private String appKey = "1665df593b51d6c7e832619cd988a5fd";
    private List<ModelNews> mDatas;

    private boolean isPrepared;

    public TabFragment(String tag) {
        this.tag = tag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tabView = inflater.inflate(R.layout.fragment_tab, null);
//        recyclerView = (RecyclerView) tabView.findViewById(R.id.tabRecyclerView);
        lvTab = (ListView) tabView.findViewById(R.id.lvTab);
        isPrepared = true;
        lazyLoad();
        return tabView;
    }

    private void bindData() {
        Map<String, String> map = new HashMap<>();
        map.put("key", appKey);
        map.put("type", tag);
        Log.i("KK", "发送请求" + tag);

        lvTab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ModelNews info = mDatas.get(position);
                Intent intent = new Intent(getActivity(), ActivityBrowser.class);
                intent.putExtra("url", info.url);
                startActivity(intent);
            }
        });


        OkHttpHelper.getInstance().post(url, map, new BaseCallBack<String>() {
            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response, String s) {

            }

            @Override
            public void onSuccess(Response response, String s) {
                Log.i("KK", s);
                try {
                    JSONObject object = new JSONObject(s);
                    JSONObject result = object.getJSONObject("result");
                    String data = result.getString("data");
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<ModelNews>>(){}.getType();
                    List<ModelNews> datas = gson.fromJson(data, type);
                    mDatas = datas;
                    tabAdapter = new TabAdapter(getActivity(), mDatas);
                    lvTab.setAdapter(tabAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    @Override
    protected void lazyLoad() {
        if(!isVisible || !isPrepared){
            return;
        }

        bindData();

    }
}
