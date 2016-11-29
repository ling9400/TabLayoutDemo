package com.qk.tablayoutindicator.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qk.tablayoutindicator.R;
import com.qk.tablayoutindicator.model.ModelNews;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by qk on 2016/11/10.
 */

public class TabAdapter extends BaseAdapter {
    private Context myContext;
    private List<ModelNews> mDatas;

    public TabAdapter(Context myContext, List<ModelNews> mDatas) {
        this.myContext = myContext;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = View.inflate(myContext, R.layout.item_tab, null);
            holder = new ViewHolder();
            holder.ivNewsPhoto = (ImageView) convertView.findViewById(R.id.ivNewsPhoto);
            holder.tvNewsTitle = (TextView) convertView.findViewById(R.id.tvNewsTitle);
            holder.tvNewTime = (TextView) convertView.findViewById(R.id.tvNewsTime);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        ModelNews info = mDatas.get(position);
        Picasso.with(myContext).load(info.thumbnail_pic_s).into(holder.ivNewsPhoto);
        holder.tvNewsTitle.setText(info.title);
        holder.tvNewTime.setText(info.date);

        return convertView;
    }

    class ViewHolder{
        public ImageView ivNewsPhoto;
        public TextView tvNewsTitle;
        public TextView tvNewTime;
    }

}
