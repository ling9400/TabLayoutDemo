package com.qk.tablayoutindicator.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qk.tablayoutindicator.R;
import com.qk.tablayoutindicator.model.ModelTab;

import java.util.List;

/**
 * Created by qk on 2016/11/26.
 */

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolderCircle> {
    private Context myContext;
    private List<ModelTab> data;
    private LayoutInflater inflater;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        public void onItemClick(View view, int position);
    }

    public AdapterRecycler(Context myContext, List<ModelTab> data, OnItemClickListener listener) {
        this.myContext = myContext;
        this.data = data;
        this.listener = listener;
        inflater = LayoutInflater.from(myContext);
    }

    @Override
    public ViewHolderCircle onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_recycler, null, false);
        ViewHolderCircle holderCircle = new ViewHolderCircle(view);
        return holderCircle;
    }

    @Override
    public void onBindViewHolder(final ViewHolderCircle holder, final int position) {
        final ModelTab info = data.get(position);
        holder.tvContent.setText(info.value);
        if(info.isSelect){
            holder.tvContent.setSelected(true);
        }else{
            holder.tvContent.setSelected(false);
        }
        holder.tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClick(v, position);
                }
            }
        });
    }

    //默认从0开始
    public void setCurrentSelect(int position){
        if(data != null && data.size() >= position){
            for(int i =0 ; i < data.size(); i++){
                ModelTab info = data.get(i);
                if(i == position){
                    info.isSelect = true;
                }else {
                    info.isSelect = false;
                }
            }
            this.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolderCircle extends RecyclerView.ViewHolder{
        private TextView tvContent;
        public ViewHolderCircle(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
